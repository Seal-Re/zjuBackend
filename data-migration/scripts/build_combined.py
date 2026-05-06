"""Build a single combined-V2.5 SQL from extracted/0723 + extracted/1014 INSERT data.

Steps:
1. Parse INSERT INTO blocks per table (handle multi-statement, balanced parens).
2. Apply per-table column transformations (drop/append cols).
3. Apply per-row int-PK shifts on 1014 side (+100000).
4. Emit a single .sql with V2.5-aligned INSERT statements (no DDL).
   The V2.5 DDL itself comes from fastop/dataset/260302.sql separately.
"""
import re
import sys
from pathlib import Path


def split_rows(values_blob: str) -> list[list[str]]:
    """Tokenize VALUES (...),(...),(...) into list of field-string lists, respecting quotes/escapes."""
    rows = []
    fields_cur = []
    field_buf = []
    depth = 0
    in_str = False
    esc = False

    def flush_field():
        fields_cur.append("".join(field_buf))
        field_buf.clear()

    for ch in values_blob:
        if esc:
            field_buf.append(ch)
            esc = False
            continue
        if ch == "\\":
            field_buf.append(ch)
            esc = True
            continue
        if ch == "'":
            in_str = not in_str
            field_buf.append(ch)
            continue
        if not in_str:
            if ch == "(":
                if depth == 0:
                    field_buf = []
                    fields_cur = []
                else:
                    field_buf.append(ch)
                depth += 1
                continue
            if ch == ")":
                depth -= 1
                if depth == 0:
                    flush_field()
                    rows.append(fields_cur)
                    fields_cur = []
                else:
                    field_buf.append(ch)
                continue
            if ch == "," and depth == 1:
                flush_field()
                continue
        field_buf.append(ch)
    return rows


def read_inserts(path: Path, table: str) -> list[list[str]]:
    text = path.read_text(encoding="utf-8", errors="replace")
    rows = []
    pos = 0
    pattern = rf"INSERT INTO `{re.escape(table)}` VALUES "
    while True:
        m = re.search(pattern, text[pos:])
        if not m:
            break
        start = pos + m.end()
        end = text.find(";\n", start)
        if end == -1:
            end = len(text)
        blob = text[start:end]
        rows.extend(split_rows(blob))
        pos = end + 1
    return rows


def emit_insert(table: str, columns: list[str], rows: list[list[str]], out, chunk_size: int = 500):
    if not rows:
        return
    col_list = ", ".join(f"`{c}`" for c in columns)
    for i in range(0, len(rows), chunk_size):
        chunk = rows[i:i + chunk_size]
        values = ",".join("(" + ",".join(r) + ")" for r in chunk)
        out.write(f"INSERT INTO `{table}` ({col_list}) VALUES {values};\n")


def shift_int(field: str, offset: int) -> str:
    if field.upper() == "NULL":
        return field
    try:
        return str(int(field) + offset)
    except ValueError:
        return field


# Column lists in V2.5 order (from 260302.sql)
V25_COLS = {
    "base_struct": ["base_id", "model", "profession", "subsystem"],
    "exe_function": [
        "exe_function_id", "function_name", "plan_id", "function_id", "version", "flow_version",
        "num", "security", "expect_time", "test_caution_id", "subject_source_id", "exe_function_order",
        "current_step_num", "exe_status", "verify_status", "verify_num", "military_status", "military_num",
        "version_description", "military", "key_pro_count", "change_flag", "flow_version_line", "depends_on",
        "result_comments", "caution", "is_ready", "start_time", "end_time", "deleted", "created_at",
        "updated_at", "created_by", "updated_by", "cal_before_time", "cal_time", "execute_time",
        "redo_count", "detect_id"
    ],
    "exe_log": ["log_id", "step_id", "plan_id", "content", "create_time"],
    "exe_step": [
        "exe_step_id", "step_id", "exe_function_id", "step_level", "step_order", "level_seq", "step_seq",
        "step_description", "content_id", "exe_status", "verify_status", "military_status", "result_comments",
        "military_comment", "step_result", "data_id", "is_manual", "level_one_id", "judge_result", "can_next",
        "command_data", "fail_cause", "operation", "operation_object", "operation_content",
        "criterion_standard_id", "criterion_standard", "criterion_type", "criterion_value_unit",
        "criterion_content", "criterion_desc", "guide_url", "key_process", "depend_on_device", "caution",
        "commander", "verfier", "soldier", "start_time", "end_time", "change_flag", "deleted",
        "created_at", "updated_at", "created_by", "updated_by", "calculate", "parallel_execute"
    ],
    "function_suite": [
        "id", "test_fun_id", "fun_num", "fun_version", "fun_order", "depends_on", "suite_id",
        "deleted", "created_at", "updated_at", "created_by", "updated_by"
    ],
    "test_base": [
        "id", "name", "entity_struct_id", "fun_group_id", "base_type", "deleted", "created_at",
        "updated_at", "created_by", "updated_by", "model", "profession", "subsystem"
    ],
    "test_function": [
        "fun_id", "fun_name", "fun_order", "test_base_id", "version", "flow_version", "plane_effect_min",
        "plane_effect_max", "num", "expect_time", "security_level", "comment", "version_description",
        "approve_comment", "subject_source_id", "other_tech_files", "device_pool", "test_caution_id",
        "caution", "depends_on", "change_flag", "key_pro_count", "approve_status", "appr_chain", "military",
        "using_by", "designer", "proofer", "verifier", "checker", "qualityer", "approver",
        "history_version_line", "sync_plan", "deleted", "created_at", "updated_at", "created_by",
        "updated_by", "create_new", "military_func", "detect_id"
    ],
    "test_function_case": [
        "case_id", "case_name", "change_user", "case_description", "case_note", "case_date",
        "module_id", "case_status", "updated"
    ],
    "test_function_module": [
        "module_id", "module_name", "change_user", "module_description", "module_note", "module_date",
        "fun_id", "module_status", "updated"
    ],
    "test_function_rely": [
        "test_function_rely_id", "suite_id", "test_function_id", "rely_function_id", "rely_funtion_ready",
        "deleted", "created_at", "updated_at", "created_by", "updated_by"
    ],
    "test_function_step": [
        "step_id", "step_name", "change_user", "step_description", "step_note", "step_date",
        "step_operation", "step_obj", "step_purpose", "total_send", "condition_status", "case_id",
        "step_status", "updated"
    ],
    "test_plan": [
        "plan_id", "entity_struct_id", "entity_id", "subject_id", "fun_group_id", "suite_id", "military",
        "plan_start_time", "plan_end_time", "actual_start_time", "actual_end_time", "status", "plan_number",
        "plan_round", "plan_name", "area_id", "dispatcher_id", "commander_id", "executor_group_id",
        "comm_assign", "execut_assign", "verify_assign", "updatable", "archived", "deleted", "base_type",
        "created_at", "updated_at", "created_by", "updated_by", "sync", "management", "for_record_data"
    ],
    "test_suite": [
        "suite_id", "suite_name", "suite_desc", "version", "plane_effect_min", "plane_effect_max",
        "proofer", "approver", "submitter", "test_base_id", "list_appr_status", "military", "key_process",
        "appr_chain", "deleted", "created_at", "updated_at", "created_by", "updated_by", "mesdce_code"
    ],
}

# 0723-side column orders (where they differ)
COLS_0723 = {
    "test_base": [
        "id", "name", "entity_struct_id", "fun_group_id", "base_type", "deleted", "created_at",
        "updated_at", "created_by", "updated_by"
    ],  # missing model/profession/subsystem at end
    "test_plan": V25_COLS["test_plan"] + ["mesdce_code"],  # 0723 has trailing mesdce_code (drop)
}

# 1014-side column orders (where they differ)
COLS_1014 = {
    "test_function_case": [
        "case_id", "case_name", "change_user", "case_description", "case_note", "case_date",
        "module_id", "case_status"
    ],  # missing trailing 'updated'
    "test_function_module": [
        "module_id", "module_name", "change_user", "module_description", "module_note", "module_date",
        "fun_id", "module_status"
    ],
    "test_function_step": [
        "step_id", "step_name", "change_user", "step_description", "step_note", "step_date",
        "step_operation", "step_obj", "step_purpose", "total_send", "condition_status", "case_id",
        "step_status"
    ],
}

# Columns whose values must be shifted +OFFSET when source is 1014
OFFSET = 100000
SHIFTS_1014 = {
    "test_function": {"fun_id"},
    "test_suite": {"suite_id"},
    "test_function_module": {"module_id", "fun_id"},
    "test_function_case": {"case_id", "module_id"},
    "test_function_step": {"step_id", "case_id"},
    "test_plan": {"suite_id"},  # 1014.test_plan.suite_id refs shifted suite
}

# Tables to migrate from 0723 (in safe FK order)
TABLES_0723_ORDER = [
    "test_base",
    "test_function",
    "test_suite",
    "function_suite",
    "test_function_rely",
    "test_plan",
    "exe_function",
    "exe_step",
]

# Tables to migrate from 1014 (in safe FK order)
TABLES_1014_ORDER = [
    "test_function",
    "test_suite",
    "test_plan",
    "test_function_module",
    "test_function_case",
    "test_function_step",
]


def transform_0723(table: str, rows: list[list[str]]) -> list[list[str]]:
    if table == "test_base":
        # Append model='', profession='', subsystem=''
        return [r + ["''", "''", "''"] for r in rows]
    if table == "test_plan":
        # Drop trailing mesdce_code (last col)
        return [r[:-1] for r in rows]
    return rows


def transform_1014(table: str, rows: list[list[str]]) -> list[list[str]]:
    src_cols = COLS_1014.get(table, V25_COLS[table])
    target_cols = V25_COLS[table]
    shifted = SHIFTS_1014.get(table, set())

    out_rows = []
    for r in rows:
        shifted_row = []
        for col, val in zip(src_cols, r):
            if col in shifted:
                val = shift_int(val, OFFSET)
            shifted_row.append(val)
        # Pad missing trailing cols
        while len(shifted_row) < len(target_cols):
            missing_col = target_cols[len(shifted_row)]
            if missing_col == "updated":
                shifted_row.append("0")
            else:
                shifted_row.append("NULL")
        out_rows.append(shifted_row)
    return out_rows


def main():
    base = Path(__file__).resolve().parent.parent
    src_0723 = base / "extracted" / "0723"
    src_1014 = base / "extracted" / "1014"
    out_path = base / "output" / "combined_v25_data.sql"
    out_path.parent.mkdir(parents=True, exist_ok=True)

    stats = {}
    with out_path.open("w", encoding="utf-8") as out:
        out.write("-- Combined V2.5-format data from 0723 + 1014 (data-only, no DDL).\n")
        out.write("-- Apply against a database whose schema was created by 260302.sql.\n")
        out.write("/*!40101 SET NAMES utf8mb4 */;\n")
        out.write("/*!40014 SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0 */;\n")
        out.write("/*!40014 SET @OLD_UNIQUE_CHECKS=@@UNIQUE_CHECKS, UNIQUE_CHECKS=0 */;\n")
        out.write("/*!40101 SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='NO_AUTO_VALUE_ON_ZERO' */;\n\n")

        # base_struct seed (V2.5)
        out.write("-- base_struct: V2.5 seed (from 260302.sql)\n")
        out.write("INSERT INTO `base_struct` (`base_id`,`model`,`profession`,`subsystem`) VALUES "
                  "(1,'M1','P1','S1'),(2,'M1','P1','S2'),(3,'M2','P2','s1'),(4,'M2','P1','s1');\n\n")
        stats["base_struct(seed)"] = 4

        # 0723 first (FK-safe order), no shift
        out.write("-- ==================== 0723 data (no shift) ====================\n")
        for table in TABLES_0723_ORDER:
            sql_file = src_0723 / f"{table}.sql"
            if not sql_file.exists():
                continue
            rows = read_inserts(sql_file, table)
            rows = transform_0723(table, rows)
            emit_insert(table, V25_COLS[table], rows, out)
            stats[f"0723.{table}"] = len(rows)
            print(f"  0723.{table}: {len(rows)} rows")

        # 1014 second (FK-safe order), shift +100000 on int PKs
        out.write("\n-- ==================== 1014 data (int PK + 100000) ====================\n")
        for table in TABLES_1014_ORDER:
            sql_file = src_1014 / f"{table}.sql"
            if not sql_file.exists():
                continue
            rows = read_inserts(sql_file, table)
            rows = transform_1014(table, rows)
            emit_insert(table, V25_COLS[table], rows, out)
            stats[f"1014.{table}"] = len(rows)
            print(f"  1014.{table}: {len(rows)} rows (shifted)")

        out.write("\n/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;\n")
        out.write("/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;\n")
        out.write("/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;\n")

    print(f"\nWrote {out_path} ({out_path.stat().st_size} bytes)")
    print("Stats:", stats)


if __name__ == "__main__":
    main()
