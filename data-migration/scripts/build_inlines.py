"""Inline V1 lookup tables (test_caution, subject_source, test_function_group) into V2.5 varchar fields.

Output supplemental_inlines.sql with UPDATE statements applied AFTER data load.
"""
import re
import sys
from pathlib import Path

sys.path.insert(0, str(Path(__file__).resolve().parent))
from build_combined import read_inserts

BASE = Path(__file__).resolve().parent.parent
SRC = BASE / "extracted" / "0723"
OUT = BASE / "output" / "supplemental_inlines.sql"


def unq(v: str) -> str | None:
    """Unquote a SQL field literal (drop surrounding ', unescape). Return None if NULL."""
    v = v.strip()
    if v.upper() == "NULL":
        return None
    if v.startswith("'") and v.endswith("'"):
        return v[1:-1].replace("\\'", "'").replace("\\\\", "\\")
    return v


def sql_escape(s: str) -> str:
    return s.replace("\\", "\\\\").replace("'", "\\'")


def main():
    # Build lookup maps
    caution_rows = read_inserts(SRC / "test_caution.sql", "test_caution")
    # cols: test_caution_id, caution_content, subject_id, subject_name, deleted, ...
    caution_map: dict[int, str] = {}
    for r in caution_rows:
        cid = unq(r[0])
        ctext = unq(r[1])
        if cid and ctext:
            try:
                caution_map[int(cid)] = ctext
            except ValueError:
                pass
    print(f"  caution lookup: {len(caution_map)} entries")

    source_rows = read_inserts(SRC / "subject_source.sql", "subject_source")
    # cols: subject_source_id, source_num, source_version, source_desc, subject_id, subject_name, deleted, ...
    source_map: dict[int, str] = {}
    for r in source_rows:
        sid = unq(r[0])
        num = unq(r[1])
        ver = unq(r[2]) or ""
        desc = unq(r[3]) or ""
        if sid:
            try:
                txt = f"{num}({ver}) {desc}".strip()
                source_map[int(sid)] = txt
            except ValueError:
                pass
    print(f"  subject_source lookup: {len(source_map)} entries")

    grp_rows = read_inserts(SRC / "test_function_group.sql", "test_function_group")
    # cols: fungrp_id, fungrp_name, subject_id, subject_name, fungrp_desc, num, system_type, deleted, ...
    grp_map: dict[int, str] = {}
    for r in grp_rows:
        gid = unq(r[0])
        gname = unq(r[1]) or ""
        sname = unq(r[3]) or ""
        if gid:
            try:
                txt = f"{sname}/{gname}".strip("/")
                grp_map[int(gid)] = txt
            except ValueError:
                pass
    print(f"  test_function_group lookup: {len(grp_map)} entries")

    # Now scan 0723.test_function and emit UPDATE for each migrated row
    tf_rows = read_inserts(SRC / "test_function.sql", "test_function")
    # 0:fun_id 14:subject_source_id 17:test_caution_id
    updates_caution = []
    updates_comment = []
    for r in tf_rows:
        fun_id = unq(r[0])
        if not fun_id:
            continue
        # test_caution_id: r[17]
        ts_cid_raw = unq(r[17]) if len(r) > 17 else None
        if ts_cid_raw and ts_cid_raw not in ("", "[]"):
            # split by ; and try int
            ids = [s.strip() for s in re.split(r"[;,]", ts_cid_raw) if s.strip().isdigit()]
            texts = [caution_map[int(i)] for i in ids if int(i) in caution_map]
            if texts:
                joined = " | ".join(texts)[:1000]  # caution col is varchar(1000)
                updates_caution.append((fun_id, joined))

        # subject_source_id: r[14]
        ts_sid_raw = unq(r[14]) if len(r) > 14 else None
        if ts_sid_raw and ts_sid_raw not in ("", "[]"):
            ids = [s.strip() for s in re.split(r"[;,]", ts_sid_raw) if s.strip().isdigit()]
            texts = [source_map[int(i)] for i in ids if int(i) in source_map]
            if texts:
                joined = " | ".join(texts)[:100]  # comment varchar(100)
                updates_comment.append((fun_id, joined))

    # test_base lookup: fun_group_id -> grp_map text -> set test_base.name (if NULL)
    tb_rows = read_inserts(SRC / "test_base.sql", "test_base")
    # 0:id 1:name 3:fun_group_id
    updates_test_base_name = []
    for r in tb_rows:
        bid = unq(r[0])
        bname = unq(r[1])
        gid_raw = unq(r[3]) if len(r) > 3 else None
        if not bid:
            continue
        if (bname is None or bname == "") and gid_raw and gid_raw.isdigit():
            gid = int(gid_raw)
            if gid in grp_map:
                updates_test_base_name.append((bid, grp_map[gid][:45]))

    OUT.parent.mkdir(parents=True, exist_ok=True)
    with OUT.open("w", encoding="utf-8") as f:
        f.write("-- Inline lookup-resolved text into V2.5 varchar fields.\n")
        f.write("-- Apply AFTER fastop_v25_full.sql loads.\n\n")
        f.write("/*!40101 SET NAMES utf8mb4 */;\n\n")

        f.write(f"-- {len(updates_caution)} test_function rows: resolve test_caution_id -> caution\n")
        for fid, txt in updates_caution:
            f.write(f"UPDATE `test_function` SET `caution`='{sql_escape(txt)}' WHERE `fun_id`={fid};\n")

        f.write(f"\n-- {len(updates_comment)} test_function rows: resolve subject_source_id -> comment\n")
        for fid, txt in updates_comment:
            f.write(f"UPDATE `test_function` SET `comment`='{sql_escape(txt)}' WHERE `fun_id`={fid};\n")

        f.write(f"\n-- {len(updates_test_base_name)} test_base rows: resolve fun_group_id -> name\n")
        for bid, txt in updates_test_base_name:
            f.write(f"UPDATE `test_base` SET `name`='{sql_escape(txt)}' WHERE `id`={bid};\n")

    print(f"\nWrote {OUT}")
    print(f"  test_function caution updates: {len(updates_caution)}")
    print(f"  test_function comment updates: {len(updates_comment)}")
    print(f"  test_base name updates: {len(updates_test_base_name)}")


if __name__ == "__main__":
    main()
