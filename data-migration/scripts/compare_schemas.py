"""Compare CREATE TABLE column lists across multiple SQL files for the same logical table."""
import re
import sys
from pathlib import Path


COLUMN_RE = re.compile(r"^\s*`([^`]+)`\s+([^,\n]+?)(?:,)?\s*$")


def extract_columns(sql_path: Path, table: str) -> list[tuple[str, str]]:
    """Return list of (column_name, type_def) tuples in declared order."""
    text = sql_path.read_text(encoding="utf-8", errors="replace")
    m = re.search(rf"CREATE TABLE `{re.escape(table)}` \((.*?)\n\) ENGINE=", text, re.DOTALL)
    if not m:
        return []
    body = m.group(1)
    cols = []
    for line in body.splitlines():
        cm = COLUMN_RE.match(line)
        if cm and not cm.group(1).startswith(" "):
            name = cm.group(1)
            tdef = cm.group(2).strip()
            # Skip PRIMARY KEY / KEY / UNIQUE lines (those start with the keyword)
            if name.upper() in ("PRIMARY", "KEY", "UNIQUE", "FOREIGN", "INDEX", "FULLTEXT"):
                continue
            cols.append((name, tdef))
    return cols


def diff(target_cols, source_cols, label_t, label_s):
    target_names = [c[0] for c in target_cols]
    source_names = [c[0] for c in source_cols]
    only_t = [c for c in target_names if c not in source_names]
    only_s = [c for c in source_names if c not in target_names]
    common = [c for c in target_names if c in source_names]
    print(f"  ONLY in {label_t} (target): {only_t}")
    print(f"  ONLY in {label_s} (source): {only_s}")
    print(f"  COMMON ({len(common)}): order-aligned -> {common == [c for c in source_names if c in target_names]}")


if __name__ == "__main__":
    target = Path(sys.argv[1])
    sources = [Path(s) for s in sys.argv[2:]]
    tables_target = re.findall(r"CREATE TABLE `([^`]+)`", target.read_text(encoding="utf-8", errors="replace"))
    for table in tables_target:
        print(f"\n=== {table} ===")
        tcols = extract_columns(target, table)
        print(f"  V2.5 target ({target.name}): {len(tcols)} cols")
        for src in sources:
            scols = extract_columns(src, table)
            if not scols:
                print(f"  {src.parent.name}/{src.name}: <not present>")
                continue
            print(f"  {src.parent.name}/{src.name}: {len(scols)} cols")
            diff(tcols, scols, "V2.5", src.parent.name)
