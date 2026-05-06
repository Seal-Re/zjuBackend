"""Supplemental V2.5 migration: operation_log, device, test_steps + synth module/case.

Outputs data-migration/output/supplemental_v25_data.sql to be sourced AFTER combined_v25_data.sql.
"""
import re
import sys
import uuid
from pathlib import Path

sys.path.insert(0, str(Path(__file__).resolve().parent))
from build_combined import read_inserts  # reuse parser

BASE = Path(__file__).resolve().parent.parent
SRC_0723 = BASE / "extracted" / "0723"
OUT = BASE / "output" / "supplemental_v25_data.sql"

SYNTH_OFFSET = 200000  # offset for synthesized module/case/step IDs
DEVICE_NS = uuid.UUID("00000000-0000-0000-0000-000000000001")  # deterministic UUID namespace


def emit_chunk(out, table: str, columns: list[str], rows: list[list[str]], chunk_size: int = 500):
    if not rows:
        return
    col_list = ", ".join(f"`{c}`" for c in columns)
    for i in range(0, len(rows), chunk_size):
        chunk = rows[i:i + chunk_size]
        values = ",".join("(" + ",".join(r) + ")" for r in chunk)
        out.write(f"INSERT INTO `{table}` ({col_list}) VALUES {values};\n")


def sql_str_or_null(value: str | None) -> str:
    if value is None:
        return "NULL"
    if value.upper() == "NULL":
        return "NULL"
    if value.startswith("'") and value.endswith("'"):
        return value
    # Escape ' and \ for safety
    return "'" + value.replace("\\", "\\\\").replace("'", "\\'") + "'"


def truncate_quoted(value: str, max_chars: int) -> str:
    """Truncate a SQL-quoted string literal to max_chars (counting actual unquoted content)."""
    if value.upper() == "NULL":
        return value
    if not (value.startswith("'") and value.endswith("'")):
        return value
    body = value[1:-1]
    # Naive char count (Python char ~ MySQL utf8mb4 char). Truncate.
    if len(body) > max_chars:
        body = body[:max_chars]
        # Ensure not ending in odd backslash
        while body.endswith("\\") and not body.endswith("\\\\"):
            body = body[:-1]
    return "'" + body + "'"


def build_operation_log(out):
    """0723.operation_log -> V2.5.operation_log

    0723 cols: operation_log_id, operation_name, operation_status, user_ip, user, timestamp,
               operation_object_id, operation_desc, created_at, updated_at, created_by, updated_by,
               deleted, request_data
    V2.5 cols: id, operator_id, operator_name, module, action, target_type, target_id, detail, ip, create_time
    """
    rows_in = read_inserts(SRC_0723 / "operation_log.sql", "operation_log")
    target_cols = ["id", "operator_id", "operator_name", "module", "action", "target_type", "target_id", "detail", "ip", "create_time"]
    rows_out = []
    for r in rows_in:
        op_id = r[0]
        op_name = r[1]
        # operation_status not used
        user_ip = r[3]
        user = r[4]
        timestamp = r[5]
        op_obj_id = r[6]
        op_desc = r[7]
        # split operation_name into module/action heuristically: "测试计划-创建" -> module="测试计划", action="创建"
        op_name_unquoted = op_name[1:-1] if op_name.startswith("'") else None
        module_val = "NULL"
        action_val = op_name
        if op_name_unquoted and "-" in op_name_unquoted:
            parts = op_name_unquoted.split("-", 1)
            module_val = "'" + parts[0].replace("'", "\\'") + "'"
            action_val = "'" + parts[1].replace("'", "\\'") + "'"
        rows_out.append([
            op_id,                                              # id (bigint, reuse 0723 int)
            "NULL",                                             # operator_id (no ID system in V1 audit)
            user,                                               # operator_name
            truncate_quoted(module_val, 64),                    # module varchar(64)
            truncate_quoted(action_val, 64),                    # action varchar(64)
            "NULL",                                             # target_type
            truncate_quoted(op_obj_id, 128),                    # target_id varchar(128)
            truncate_quoted(op_desc, 500),                      # detail varchar(500)
            truncate_quoted(user_ip, 64),                       # ip varchar(64)
            timestamp,                                          # create_time
        ])
    emit_chunk(out, "operation_log", target_cols, rows_out)
    return len(rows_out)


def build_device(out):
    """0723.device -> V2.5.device

    0723 cols (relevant): device_id, device_name, device_serial, dev_cat_id, status, device_desc,
                          deleted, created_at, updated_at, created_by, updated_by, remark
    V2.5 cols: id (UUID), code, name, type, status, description, deleted, created_at, updated_at, created_by, updated_by
    """
    rows_in = read_inserts(SRC_0723 / "device.sql", "device")
    target_cols = ["id", "code", "name", "type", "status", "description", "deleted", "created_at", "updated_at", "created_by", "updated_by"]
    rows_out = []
    seen_codes = set()
    for r in rows_in:
        device_id = r[0]  # int
        device_name = r[1]
        device_serial = r[2]
        dev_cat_id = r[3]
        status = r[13]  # 14th col by 0723 schema (0-indexed 13)
        device_desc = r[15]
        deleted = r[17]
        created_at = r[18]
        updated_at = r[19]
        created_by = r[20]
        updated_by = r[21]

        # Generate deterministic UUID from device_id
        try:
            int_id = int(device_id)
            new_uuid = str(uuid.uuid5(DEVICE_NS, f"V1-DEV-{int_id}"))
        except (ValueError, TypeError):
            new_uuid = str(uuid.uuid4())

        # Code: prefer device_serial, fallback to V1-DEV-<id>
        if device_serial.upper() == "NULL" or device_serial == "''":
            code_val = f"'V1-DEV-{device_id}'"
        else:
            code_val = device_serial

        # Name fallback (V2.5.name is NOT NULL): prefer device_name, fallback to code or V1-DEV-<id>
        if device_name.upper() == "NULL" or device_name == "''":
            device_name = code_val if code_val.upper() != "NULL" else f"'V1-DEV-{device_id}'"

        # Status remap: V1 0:无效 1:可用 2:繁忙 3:故障 -> V2 0:离线 1:在线 2:故障 3:维护
        status_map = {"0": "0", "1": "1", "2": "1", "3": "2"}
        status_str = status if status.upper() != "NULL" else "0"
        v25_status = status_map.get(status_str.strip("'"), status_str)

        # Type: V1 dev_cat_id is int FK; V2.5 type is varchar. Wrap as 'cat-<n>' string.
        if dev_cat_id.upper() == "NULL":
            type_val = "NULL"
        else:
            type_val = f"'cat-{dev_cat_id}'"

        # Dedup code (UNIQUE constraint on code+deleted)
        code_key = code_val + "|" + deleted
        if code_key in seen_codes:
            code_val = f"'V1-DEV-{device_id}-{int_id}'"
            code_key = code_val + "|" + deleted
        seen_codes.add(code_key)

        rows_out.append([
            f"'{new_uuid}'",
            truncate_quoted(code_val, 64),
            truncate_quoted(device_name, 128),
            truncate_quoted(type_val, 64),
            v25_status,
            truncate_quoted(device_desc, 500),
            deleted,
            created_at,
            updated_at,
            truncate_quoted(created_by, 64),
            truncate_quoted(updated_by, 64),
        ])
    emit_chunk(out, "device", target_cols, rows_out)
    return len(rows_out)


def build_test_steps_synth(out):
    """0723.test_steps -> V2.5.test_function_step + synth test_function_module + test_function_case.

    Each unique fun_id gets:
      - synth module_id = SYNTH_OFFSET + fun_id, module_name = '[V1] mod', fun_id, module_status=0, updated=1
      - synth case_id = SYNTH_OFFSET + fun_id, module_id = SYNTH_OFFSET + fun_id, case_status=0, updated=1
    Each test_steps row:
      - step_id = SYNTH_OFFSET + step_id
      - case_id = SYNTH_OFFSET + fun_id
      - step_name = '步骤'+step_seq, step_description=V1.step_description (trunc 100), step_operation=V1.operation,
        step_obj=V1.operation_object (trunc 100), step_purpose=V1.operation_content (trunc 100),
        change_user=V1.created_by, step_date=V1.created_at, step_status=0, updated=1
    """
    rows_in = read_inserts(SRC_0723 / "test_steps.sql", "test_steps")
    fun_ids_seen = set()
    module_rows = []
    case_rows = []
    step_rows = []

    # 0723.test_steps cols (by index):
    # 0:step_id 1:fun_id 2:old_step_id 3:step_node_level 4:step_order 5:level_seq 6:step_seq
    # 7:step_description 8:level_one_id 9:command_data 10:operation 11:operation_object
    # 12:criterion_standard_id 13:criterion_standard 14:operation_content 15:criterion_type
    # 16:criterion_value_unit 17:criterion_content 18:criterion_desc 19:depend_on_device 20:caution
    # 21:change_flag 22:guide_url 23:key_process 24:deleted 25:created_at 26:updated_at
    # 27:created_by 28:updated_by 29:calculate

    for r in rows_in:
        if len(r) < 28:
            continue
        v1_step_id = r[0]
        v1_fun_id = r[1]
        step_seq = r[6]
        step_desc = r[7]
        operation = r[10]
        op_object = r[11]
        op_content = r[14]
        deleted = r[24] if len(r) > 24 else "0"
        created_at = r[25] if len(r) > 25 else "NULL"
        created_by = r[27] if len(r) > 27 else "NULL"

        if v1_fun_id.upper() == "NULL":
            continue
        try:
            fid = int(v1_fun_id)
        except ValueError:
            continue

        if fid not in fun_ids_seen:
            fun_ids_seen.add(fid)
            synth_mid = SYNTH_OFFSET + fid
            # module: module_id, module_name, change_user, module_description, module_note, module_date, fun_id, module_status, updated
            module_rows.append([
                str(synth_mid), "'[V1] 默认模块'", "NULL", "'V1迁移自动合成的模块层'", "NULL",
                created_at, str(fid), "0", "1"
            ])
            synth_cid = SYNTH_OFFSET + fid
            # case: case_id, case_name, change_user, case_description, case_note, case_date, module_id, case_status, updated
            case_rows.append([
                str(synth_cid), "'[V1] 默认用例'", "NULL", "'V1迁移自动合成的用例层'", "NULL",
                created_at, str(synth_mid), "0", "1"
            ])

        synth_step_id = SYNTH_OFFSET + int(v1_step_id)
        synth_case_id = SYNTH_OFFSET + fid
        # step: step_id, step_name, change_user, step_description, step_note, step_date,
        #       step_operation, step_obj, step_purpose, total_send, condition_status, case_id, step_status, updated
        step_name = f"'步骤{step_seq[1:-1] if step_seq.startswith(chr(39)) else v1_step_id}'"
        step_rows.append([
            str(synth_step_id),
            truncate_quoted(step_name, 100),
            truncate_quoted(created_by, 100),
            truncate_quoted(step_desc, 100),
            "NULL",
            truncate_quoted(created_at, 100),
            truncate_quoted(operation, 100),
            truncate_quoted(op_object, 100),
            truncate_quoted(op_content, 100),
            "NULL",
            "NULL",
            str(synth_case_id),
            "0",
            "1",
        ])

    module_cols = ["module_id", "module_name", "change_user", "module_description", "module_note", "module_date", "fun_id", "module_status", "updated"]
    case_cols = ["case_id", "case_name", "change_user", "case_description", "case_note", "case_date", "module_id", "case_status", "updated"]
    step_cols = ["step_id", "step_name", "change_user", "step_description", "step_note", "step_date", "step_operation", "step_obj", "step_purpose", "total_send", "condition_status", "case_id", "step_status", "updated"]

    emit_chunk(out, "test_function_module", module_cols, module_rows)
    emit_chunk(out, "test_function_case", case_cols, case_rows)
    emit_chunk(out, "test_function_step", step_cols, step_rows)

    return len(module_rows), len(case_rows), len(step_rows)


def main():
    OUT.parent.mkdir(parents=True, exist_ok=True)
    with OUT.open("w", encoding="utf-8") as out:
        out.write("-- Supplemental V2.5 data: 0723 cross-table business-logic remaps.\n")
        out.write("-- Apply AFTER combined_v25_data.sql.\n")
        out.write("/*!40101 SET NAMES utf8mb4 */;\n")
        out.write("/*!40014 SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0 */;\n")
        out.write("/*!40014 SET @OLD_UNIQUE_CHECKS=@@UNIQUE_CHECKS, UNIQUE_CHECKS=0 */;\n")
        out.write("/*!40101 SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='NO_AUTO_VALUE_ON_ZERO' */;\n\n")

        out.write("-- ==================== 0723.operation_log -> V2.5.operation_log ====================\n")
        n_op = build_operation_log(out)
        print(f"  operation_log: {n_op} rows")

        out.write("\n-- ==================== 0723.device -> V2.5.device ====================\n")
        n_dev = build_device(out)
        print(f"  device: {n_dev} rows")

        out.write("\n-- ==================== 0723.test_steps -> synth module/case + V2.5.test_function_step ====================\n")
        n_mod, n_case, n_step = build_test_steps_synth(out)
        print(f"  synth module: {n_mod}, synth case: {n_case}, test_function_step: {n_step}")

        out.write("\n/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;\n")
        out.write("/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;\n")
        out.write("/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;\n")

    print(f"\nWrote {OUT} ({OUT.stat().st_size:,} bytes)")


if __name__ == "__main__":
    main()
