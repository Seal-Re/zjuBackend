"""Extract DDL-only from a mysqldump file: keep DROP/CREATE/SET, drop INSERT and LOCK TABLES blocks."""
import re
import sys
from pathlib import Path

src = Path(sys.argv[1])
dst = Path(sys.argv[2])

text = src.read_text(encoding="utf-8", errors="replace")
out_lines = []
in_data_block = False
in_lock = False

for line in text.splitlines(keepends=True):
    stripped = line.strip()
    if stripped.startswith("-- Dumping data for table"):
        in_data_block = True
        continue
    if stripped.startswith("-- Table structure for table") or stripped.startswith("--") and stripped.endswith("--"):
        in_data_block = False
    if in_data_block:
        continue
    if stripped.startswith("LOCK TABLES"):
        in_lock = True
        continue
    if stripped.startswith("UNLOCK TABLES"):
        in_lock = False
        continue
    if in_lock:
        continue
    if stripped.startswith("INSERT INTO"):
        continue
    # Drop GTID_PURGED (binlog state from source) and SQL_LOG_BIN session toggle (replication-only).
    if "GTID_PURGED" in line:
        continue
    if "SQL_LOG_BIN" in line:
        continue
    out_lines.append(line)

dst.write_text("".join(out_lines), encoding="utf-8")
print(f"Wrote {dst} ({dst.stat().st_size} bytes)")
