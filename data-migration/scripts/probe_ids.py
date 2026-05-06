"""Probe min/max int PK from extracted INSERT INTO files."""
import re
import sys
from pathlib import Path


def split_rows(values_blob: str) -> list[str]:
    """Split MySQL VALUES (...),(...),... into row tuples respecting strings."""
    rows = []
    depth = 0
    in_str = False
    esc = False
    cur = []
    for ch in values_blob:
        if esc:
            cur.append(ch)
            esc = False
            continue
        if ch == "\\":
            cur.append(ch)
            esc = True
            continue
        if ch == "'":
            in_str = not in_str
            cur.append(ch)
            continue
        if not in_str:
            if ch == "(":
                if depth == 0:
                    cur = []
                else:
                    cur.append(ch)
                depth += 1
                continue
            if ch == ")":
                depth -= 1
                if depth == 0:
                    rows.append("".join(cur))
                else:
                    cur.append(ch)
                continue
        cur.append(ch)
    return rows


def split_fields(row: str) -> list[str]:
    fields = []
    depth = 0
    in_str = False
    esc = False
    cur = []
    for ch in row:
        if esc:
            cur.append(ch)
            esc = False
            continue
        if ch == "\\":
            cur.append(ch)
            esc = True
            continue
        if ch == "'":
            in_str = not in_str
            cur.append(ch)
            continue
        if not in_str and ch == ",":
            fields.append("".join(cur).strip())
            cur = []
            continue
        cur.append(ch)
    fields.append("".join(cur).strip())
    return fields


def probe(path: Path, table: str, col_idx: int) -> dict:
    text = path.read_text(encoding="utf-8", errors="replace")
    rows = []
    pos = 0
    pattern = rf"INSERT INTO `{re.escape(table)}` VALUES "
    while True:
        m = re.search(pattern, text[pos:])
        if not m:
            break
        start = pos + m.end()
        # Find statement end ";" at start of line or after final )
        end = text.find(";\n", start)
        if end == -1:
            end = len(text)
        blob = text[start:end]
        rows.extend(split_rows(blob))
        pos = end + 1
    ids = []
    for r in rows:
        f = split_fields(r)
        if len(f) <= col_idx:
            continue
        v = f[col_idx]
        if v.lower() == "null":
            continue
        v = v.strip("'\"")
        try:
            ids.append(int(v))
        except ValueError:
            continue
    return {
        "rows": len(rows),
        "id_count": len(ids),
        "min": min(ids) if ids else None,
        "max": max(ids) if ids else None,
    }


if __name__ == "__main__":
    targets = [
        ("data-migration/extracted/0723/test_function.sql", "test_function", 0),
        ("data-migration/extracted/1014/test_function.sql", "test_function", 0),
        ("data-migration/extracted/0723/test_suite.sql", "test_suite", 0),
        ("data-migration/extracted/1014/test_suite.sql", "test_suite", 0),
        ("data-migration/extracted/0723/test_base.sql", "test_base", 0),
        ("data-migration/extracted/0723/function_suite.sql", "function_suite", 0),
        ("data-migration/extracted/0723/test_plan.sql", "test_plan", 0),  # PK is UUID, just count
        ("data-migration/extracted/1014/test_plan.sql", "test_plan", 0),
        ("data-migration/extracted/1014/test_function_module.sql", "test_function_module", 0),
        ("data-migration/extracted/1014/test_function_case.sql", "test_function_case", 0),
        ("data-migration/extracted/1014/test_function_step.sql", "test_function_step", 0),
        ("data-migration/extracted/0723/exe_function.sql", "exe_function", 0),  # UUID
        ("data-migration/extracted/0723/exe_step.sql", "exe_step", 0),  # UUID
    ]
    for path, table, col in targets:
        p = Path(path)
        if not p.exists():
            print(f"{path}: <missing>")
            continue
        r = probe(p, table, col)
        print(f"{path}: {r}")
