"""Verify V1→V2 column consolidation in 0723.exe_step.

For each consolidatable column (command_data, judge_result, criterion_*, fail_cause, operation, operation_object),
report:
  - row count
  - non-NULL count
  - non-empty count (not NULL and not '')
  - sample 3 non-empty values

Then count exe_step_command and exe_step_jugement rows + how many distinct exe_step_id they reference.

Decision rule:
  - If exe_step.command_data non-NULL fraction >> exe_step_command rows / exe_step rows: V1 already consolidated
  - Else: must fan-in.
"""
import sys
from pathlib import Path

sys.path.insert(0, str(Path(__file__).resolve().parent))
from build_combined import read_inserts

BASE = Path(__file__).resolve().parent.parent
SRC = BASE / "extracted" / "0723"


def audit_col(rows: list[list[str]], col_idx: int, col_name: str, table: str) -> dict:
    total = len(rows)
    non_null = 0
    non_empty = 0
    samples = []
    for r in rows:
        if col_idx >= len(r):
            continue
        v = r[col_idx].strip()
        if v.upper() == "NULL":
            continue
        non_null += 1
        # strip surrounding quotes
        if v.startswith("'") and v.endswith("'"):
            body = v[1:-1]
        else:
            body = v
        if body:
            non_empty += 1
            if len(samples) < 3:
                samples.append(body[:120])
    return {
        "table": table, "col": col_name, "total": total,
        "non_null": non_null, "non_empty": non_empty,
        "samples": samples,
    }


def main():
    print("Loading 0723.exe_step ...")
    es = read_inserts(SRC / "exe_step.sql", "exe_step")
    print(f"  {len(es)} rows")

    # exe_step column indices (per V2.5 / 0723 schema, identical):
    # 0:exe_step_id 1:step_id 2:exe_function_id 3:step_level 4:step_order
    # 12:result_comments 13:military_comment 14:step_result
    # 20:command_data 21:fail_cause 22:operation 23:operation_object 24:operation_content
    # 26:criterion_standard 27:criterion_type 28:criterion_value_unit 29:criterion_content 30:criterion_desc
    # 18:judge_result

    audits = [
        audit_col(es, 20, "command_data", "exe_step"),
        audit_col(es, 21, "fail_cause", "exe_step"),
        audit_col(es, 22, "operation", "exe_step"),
        audit_col(es, 23, "operation_object", "exe_step"),
        audit_col(es, 24, "operation_content", "exe_step"),
        audit_col(es, 18, "judge_result", "exe_step"),
        audit_col(es, 26, "criterion_standard", "exe_step"),
        audit_col(es, 27, "criterion_type", "exe_step"),
        audit_col(es, 29, "criterion_content", "exe_step"),
    ]

    # exe_step_command analysis
    print("\nLoading exe_step_command ...")
    esc = read_inserts(SRC / "exe_step_command.sql", "exe_step_command")
    esc_step_ids = {r[2].strip("'") for r in esc if len(r) > 2 and r[2].upper() != "NULL"}
    print(f"  {len(esc)} rows referencing {len(esc_step_ids)} distinct exe_step_ids")

    print("\nLoading exe_step_jugement ...")
    esj = read_inserts(SRC / "exe_step_jugement.sql", "exe_step_jugement")
    esj_step_ids = {r[1].strip("'") for r in esj if len(r) > 1 and r[1].upper() != "NULL"}
    print(f"  {len(esj)} rows referencing {len(esj_step_ids)} distinct exe_step_ids")

    out = BASE / "CONSOLIDATION_AUDIT.md"
    with out.open("w", encoding="utf-8") as f:
        f.write("# V1→V2 exe_step column consolidation audit\n\n")
        f.write(f"Source: 0723.exe_step ({len(es)} rows)\n\n")
        f.write("## exe_step column population\n\n")
        f.write("| col | total | non_null | non_empty | non_empty % | samples |\n")
        f.write("|---|---|---|---|---|---|\n")
        for a in audits:
            pct = (a["non_empty"] / a["total"] * 100) if a["total"] else 0
            samples_str = " · ".join(s.replace("|", "\\|").replace("\n", "\\n") for s in a["samples"])
            f.write(f"| `{a['col']}` | {a['total']} | {a['non_null']} | {a['non_empty']} | {pct:.1f}% | {samples_str} |\n")

        f.write("\n## Subdetail tables\n\n")
        f.write(f"| table | rows | distinct exe_step_id refs | exe_step coverage % |\n")
        f.write(f"|---|---|---|---|\n")
        f.write(f"| `exe_step_command` | {len(esc)} | {len(esc_step_ids)} | {len(esc_step_ids) / len(es) * 100:.2f}% |\n")
        f.write(f"| `exe_step_jugement` | {len(esj)} | {len(esj_step_ids)} | {len(esj_step_ids) / len(es) * 100:.2f}% |\n")

        # Decision per column
        f.write("\n## Decision\n\n")
        cmd_audit = [a for a in audits if a["col"] == "command_data"][0]
        jr_audit = [a for a in audits if a["col"] == "judge_result"][0]
        cmd_pct = cmd_audit["non_empty"] / cmd_audit["total"] * 100
        jr_pct = jr_audit["non_empty"] / jr_audit["total"] * 100
        esc_cov = len(esc_step_ids) / len(es) * 100
        esj_cov = len(esj_step_ids) / len(es) * 100

        f.write(f"- `exe_step.command_data` populated {cmd_pct:.1f}% rows; `exe_step_command` covers {esc_cov:.1f}% rows.\n")
        if cmd_pct > esc_cov:
            f.write(f"  **Decision:** V1 already consolidated command data into `exe_step.command_data`; sub-detail `exe_step_command` is redundant. **DROP exe_step_command** confirmed.\n")
        else:
            f.write(f"  **Decision:** V1 did NOT consolidate; `exe_step_command` carries data not in `exe_step.command_data`. **FAN-IN required.**\n")
        f.write(f"\n- `exe_step.judge_result` populated {jr_pct:.1f}% rows; `exe_step_jugement` covers {esj_cov:.1f}% rows.\n")
        if jr_pct > esj_cov:
            f.write(f"  **Decision:** V1 already consolidated judgement; **DROP exe_step_jugement** confirmed.\n")
        else:
            f.write(f"  **Decision:** V1 did NOT consolidate; **FAN-IN required.**\n")

    print(f"\nWrote {out}")


if __name__ == "__main__":
    main()
