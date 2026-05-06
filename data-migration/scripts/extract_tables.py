"""Extract CREATE TABLE + INSERT INTO blocks for whitelisted tables from a mysqldump SQL file.

Skips views, routines, comments outside table sections, and non-whitelisted tables.
Output: one file per table with raw DDL + INSERT (no DROP), plus an index.json.
"""
import re
import sys
import json
from pathlib import Path

TABLE_RE = re.compile(r"^-- Table structure for table `([^`]+)`")
DUMP_DATA_RE = re.compile(r"^-- Dumping data for table `([^`]+)`")
END_DUMP_RE = re.compile(r"^-- Dumping (events|routines|triggers) ")


def extract(src: Path, whitelist: set[str], out_dir: Path) -> dict:
    out_dir.mkdir(parents=True, exist_ok=True)
    current = None
    keep = False
    buffers: dict[str, list[str]] = {t: [] for t in whitelist}
    counts = {t: {"ddl_lines": 0, "insert_rows": 0} for t in whitelist}

    with src.open("r", encoding="utf-8", errors="replace") as f:
        for line in f:
            m1 = TABLE_RE.match(line)
            m2 = DUMP_DATA_RE.match(line)
            m3 = END_DUMP_RE.match(line)
            if m1:
                current = m1.group(1)
                keep = current in whitelist
                if keep:
                    buffers[current].append(line)
                continue
            if m2:
                current = m2.group(1)
                keep = current in whitelist
                if keep:
                    buffers[current].append(line)
                continue
            if m3:
                current = None
                keep = False
                continue
            if keep and current:
                buffers[current].append(line)
                if line.lstrip().startswith("INSERT INTO"):
                    counts[current]["insert_rows"] += line.count("),(") + 1
                elif line.startswith("  ") or line.startswith("CREATE TABLE") or line.startswith(")"):
                    counts[current]["ddl_lines"] += 1

    for table, lines in buffers.items():
        if not lines:
            continue
        (out_dir / f"{table}.sql").write_text("".join(lines), encoding="utf-8")

    return counts


if __name__ == "__main__":
    src = Path(sys.argv[1])
    out = Path(sys.argv[2])
    whitelist = set(sys.argv[3].split(","))
    res = extract(src, whitelist, out)
    print(json.dumps(res, indent=2, ensure_ascii=False))
