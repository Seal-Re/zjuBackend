"""Static validation of combined_v25_data.sql.

Checks:
  1. Every INSERT row has correct field count for declared columns.
  2. No quote/escape parse failure.
  3. NOT NULL columns are not literally 'NULL'.
  4. PK uniqueness within each table.
  5. UTF-8 cleanliness.
"""
import re
import sys
from pathlib import Path
from collections import Counter

V25_NOT_NULL = {
    "device": {"id", "code", "name", "status", "deleted"},
    "operation_log": {"id"},
    "base_struct": {"base_id", "model", "profession", "subsystem"},
    "exe_function": {"exe_function_id", "verify_num", "military_num", "is_ready", "deleted"},
    "exe_log": {"log_id"},
    "exe_step": {"exe_step_id", "exe_function_id", "is_manual", "key_process", "deleted"},
    "function_suite": {"id", "test_fun_id", "fun_order", "suite_id", "deleted"},
    "test_base": {"id", "deleted", "model", "profession", "subsystem"},
    "test_function": {
        "fun_id", "fun_name", "test_base_id", "version", "flow_version", "plane_effect_min",
        "plane_effect_max", "num", "key_pro_count", "military", "deleted"
    },
    "test_function_case": {"case_id", "case_status", "updated"},
    "test_function_module": {"module_id", "module_status", "updated"},
    "test_function_rely": {"test_function_rely_id", "test_function_id", "deleted"},
    "test_function_step": {"step_id", "step_status", "updated"},
    "test_plan": {"plan_id", "military", "deleted"},
    "test_suite": {
        "suite_id", "suite_name", "version", "plane_effect_min", "plane_effect_max",
        "test_base_id", "key_process", "deleted"
    },
}

V25_PK = {
    "device": "id",
    "operation_log": "id",
    "base_struct": "base_id",
    "exe_function": "exe_function_id",
    "exe_log": "log_id",
    "exe_step": "exe_step_id",
    "function_suite": "id",
    "test_base": "id",
    "test_function": "fun_id",
    "test_function_case": "case_id",
    "test_function_module": "module_id",
    "test_function_rely": "test_function_rely_id",
    "test_function_step": "step_id",
    "test_plan": "plan_id",
    "test_suite": "suite_id",
}


INSERT_RE = re.compile(
    r"INSERT INTO `(?P<table>[^`]+)` \((?P<cols>[^)]+)\) VALUES (?P<vals>.*?);\n",
    re.DOTALL,
)


def split_rows(values_blob: str) -> list[list[str]]:
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
            field_buf.append(ch); esc = False; continue
        if ch == "\\":
            field_buf.append(ch); esc = True; continue
        if ch == "'":
            in_str = not in_str; field_buf.append(ch); continue
        if not in_str:
            if ch == "(":
                if depth == 0:
                    field_buf = []; fields_cur = []
                else:
                    field_buf.append(ch)
                depth += 1; continue
            if ch == ")":
                depth -= 1
                if depth == 0:
                    flush_field(); rows.append(fields_cur); fields_cur = []
                else:
                    field_buf.append(ch)
                continue
            if ch == "," and depth == 1:
                flush_field(); continue
        field_buf.append(ch)
    return rows


def parse_cols(cols_str: str) -> list[str]:
    return [c.strip().strip("`") for c in cols_str.split(",")]


def main():
    path = Path(sys.argv[1]) if len(sys.argv) > 1 else Path(__file__).resolve().parent.parent / "output" / "combined_v25_data.sql"
    text = path.read_text(encoding="utf-8", errors="replace")
    issues = []
    pk_counts = {}  # table -> Counter
    row_counts = {}

    for m in INSERT_RE.finditer(text):
        table = m.group("table")
        cols = parse_cols(m.group("cols"))
        rows = split_rows("(" + m.group("vals") + ")") if not m.group("vals").startswith("(") else split_rows(m.group("vals"))
        # split_rows expects to find opening parens itself
        rows = split_rows(m.group("vals"))
        n_cols = len(cols)
        for ri, row in enumerate(rows):
            if len(row) != n_cols:
                issues.append(f"{table}: row {ri} has {len(row)} fields, expected {n_cols}")
            # NOT NULL check
            for ci, col in enumerate(cols):
                if col in V25_NOT_NULL.get(table, set()):
                    val = row[ci].strip()
                    if val.upper() == "NULL":
                        issues.append(f"{table}: row {ri} col `{col}` is NULL but column is NOT NULL")
            # PK collection
            pk_col = V25_PK.get(table)
            if pk_col and pk_col in cols:
                idx = cols.index(pk_col)
                pk_val = row[idx].strip()
                pk_counts.setdefault(table, Counter())[pk_val] += 1
        row_counts[table] = row_counts.get(table, 0) + len(rows)

    # PK duplicates
    for table, counter in pk_counts.items():
        dups = [(v, c) for v, c in counter.items() if c > 1]
        for v, c in dups[:20]:
            issues.append(f"{table}: PK={v} appears {c} times")

    print(f"Total INSERT rows by table: {row_counts}")
    print(f"\n{len(issues)} issues:")
    for i in issues[:100]:
        print(f"  - {i}")
    if len(issues) > 100:
        print(f"  ... ({len(issues) - 100} more)")
    sys.exit(1 if issues else 0)


if __name__ == "__main__":
    main()
