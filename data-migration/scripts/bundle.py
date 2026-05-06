"""Concatenate all restore parts into a single self-contained SQL bundle."""
from pathlib import Path

base = Path(__file__).resolve().parent.parent
project = base.parent

parts = [
    ("V2.5 base schema (260302 DDL only)", base / "output" / "v25_schema.sql"),
    ("Migration: alter_step_device_ems", project / "fastop" / "dataset" / "alter_step_device_ems.sql"),
    ("Migration: alter_test_plan_remark", project / "fastop" / "dataset" / "alter_test_plan_remark.sql"),
    ("Migration: device table", project / "fastop" / "dataset" / "device.sql"),
    ("Migration: operation_log table", project / "fastop" / "dataset" / "operation_log.sql"),
    ("Combined data (0723 + 1014 V2.5-shifted)", base / "output" / "combined_v25_data.sql"),
    ("Supplemental cross-table remap (operation_log/device/test_steps+synth)", base / "output" / "supplemental_v25_data.sql"),
    ("Supplemental inline lookups (test_caution/subject_source/test_function_group)", base / "output" / "supplemental_inlines.sql"),
]

dst = base / "output" / "fastop_v25_full.sql"
with dst.open("w", encoding="utf-8") as out:
    out.write("-- Fastop V2.5 full restore bundle (DDL + 0723+1014 combined data).\n")
    out.write("-- Apply against an empty database. Idempotent for migrations only; CREATE TABLEs DROP first.\n\n")
    out.write("SET FOREIGN_KEY_CHECKS = 0;\n")
    out.write("SET UNIQUE_CHECKS = 0;\n")
    out.write("SET SQL_MODE = 'NO_AUTO_VALUE_ON_ZERO';\n")
    out.write("SET NAMES utf8mb4;\n\n")
    for label, p in parts:
        out.write(f"\n-- ===================================================================\n")
        out.write(f"-- {label}  (source: {p.name})\n")
        out.write(f"-- ===================================================================\n")
        out.write(p.read_text(encoding="utf-8", errors="replace"))
        out.write("\n")
    out.write("\nSET FOREIGN_KEY_CHECKS = 1;\nSET UNIQUE_CHECKS = 1;\n")

print(f"Wrote {dst} ({dst.stat().st_size:,} bytes)")
