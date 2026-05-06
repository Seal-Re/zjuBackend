"""Sample top N rows from each 0723 table that we classified as 'dropped' to verify drop is correct.

Output a markdown report that justifies each drop with actual data evidence.
"""
import re
import sys
from pathlib import Path

sys.path.insert(0, str(Path(__file__).resolve().parent))
from build_combined import read_inserts

BASE = Path(__file__).resolve().parent.parent
SRC = Path("D:/AgentWorkStation/zjuBackend/data/0723.sql")

# Tables we classified as dropped, with reason groups
DROPPED = {
    "auth": [
        "access_token", "privilege", "privilege_action", "privilege_role_target",
        "privilege_target", "privilege_target_action", "role", "role_privilege",
        "user", "user_role_link",
    ],
    "exe_archive": ["exe_function_archive", "exe_step_archive"],
    "exe_subdetail": [
        "exe_step_command", "exe_step_command_result", "exe_step_jugement",
        "exe_step_img", "exe_step_video", "exe_step_speech",
    ],
    "device_subdetail": [
        "device_category", "device_command", "device_command_back", "device_data",
        "device_ep_map", "device_model", "device_monitor", "device_order",
        "device_type", "device_unit", "device_usage_info", "check_device",
        "support_device_map", "support_device_newmap", "driver",
    ],
    "cabling": ["exe_cable_dwg", "exe_cable_step", "test_cable_dwg", "test_cable_plan"],
    "lookup_dropped": [
        "test_caution", "subject_source", "test_function_group", "executor_group",
        "test_subject", "default_assign", "entity_model", "entity_structure",
        "entity_target", "paint_model", "tech_management", "tech_status",
        "system_config", "software_version",
    ],
    "ui_misc": [
        "area", "arealocation", "buttonlocation", "buttonlocation1",
        "designer_config", "pump_address", "pump_video", "c30ptest",
        "ft010_in_v", "ft010_out_v", "staff", "employee",
    ],
    "approve_history": [
        "approve_history", "approve_function_history", "approve_suite_history",
    ],
    "detect": ["detect_result"],
    "system_events": [
        "event", "manufacture_analysis", "input_technical_status",
        "spec_input_technical_status", "year_plan",
    ],
}


def extract_ddl(text: str, table: str) -> list[str]:
    m = re.search(rf"CREATE TABLE `{re.escape(table)}` \((.*?)\n\) ENGINE=", text, re.DOTALL)
    if not m:
        return []
    cols = []
    for line in m.group(1).splitlines():
        cm = re.match(r"^\s*`([^`]+)`\s+(.+?)(?:,)?\s*$", line)
        if cm and cm.group(1).upper() not in ("PRIMARY", "KEY", "UNIQUE", "FOREIGN", "INDEX"):
            cols.append(f"{cm.group(1)}: {cm.group(2).strip()[:60]}")
    return cols


def main():
    text = SRC.read_text(encoding="utf-8", errors="replace")
    out = BASE / "DROPPED_SAMPLES.md"
    with out.open("w", encoding="utf-8") as f:
        f.write("# 0723 dropped-table evidence audit\n\n")
        f.write("Top 3 sample rows from every dropped 0723 table, plus DDL summary, "
                "to verify the drop classification is correct.\n\n")

        for group, tables in DROPPED.items():
            f.write(f"\n## Group: `{group}`\n\n")
            for t in tables:
                cols = extract_ddl(text, t)
                if not cols:
                    f.write(f"### `{t}` — NOT FOUND in 0723.sql\n\n")
                    continue
                # find INSERT data
                rows = []
                pos = 0
                pat = rf"INSERT INTO `{re.escape(t)}` VALUES "
                while True:
                    m = re.search(pat, text[pos:])
                    if not m:
                        break
                    start = pos + m.end()
                    end = text.find(";\n", start)
                    if end == -1:
                        end = len(text)
                    blob = text[start:end]
                    # parse first 3 rows
                    from build_combined import split_rows
                    these = split_rows(blob)
                    rows.extend(these)
                    if len(rows) >= 3:
                        break
                    pos = end + 1
                f.write(f"### `{t}` — {len(rows)}+ rows, {len(cols)} cols\n\n")
                f.write("Schema:\n```\n" + "\n".join(cols) + "\n```\n\n")
                if rows:
                    f.write("Sample (first 3):\n```\n")
                    for ri, r in enumerate(rows[:3]):
                        # truncate each field to 60 chars
                        r_short = [v[:60] if isinstance(v, str) else str(v) for v in r]
                        f.write(f"  row{ri}: ({', '.join(r_short)})\n")
                    f.write("```\n\n")
    print(f"Wrote {out}")


if __name__ == "__main__":
    main()
