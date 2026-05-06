#!/bin/bash
# Validate combined bundle by loading into a throwaway MySQL container and counting rows.
set -euo pipefail
CONTAINER="${1:-fastop-mysql-validate}"
BUNDLE="${2:-D:/AgentWorkStation/zjuBackend/data-migration/output/fastop_v25_full.sql}"

echo "Loading bundle into $CONTAINER..."
docker exec -i "$CONTAINER" mysql -uroot -pFastop@123 autosys_1014 < "$BUNDLE" 2>&1 | tee /tmp/load_log.txt | tail -20 || true

echo
echo "=== Per-table row counts ==="
docker exec "$CONTAINER" mysql -uroot -pFastop@123 -N -e "
USE autosys_1014;
SELECT 'base_struct', COUNT(*) FROM base_struct
UNION ALL SELECT 'exe_function', COUNT(*) FROM exe_function
UNION ALL SELECT 'exe_log', COUNT(*) FROM exe_log
UNION ALL SELECT 'exe_step', COUNT(*) FROM exe_step
UNION ALL SELECT 'function_suite', COUNT(*) FROM function_suite
UNION ALL SELECT 'test_base', COUNT(*) FROM test_base
UNION ALL SELECT 'test_function', COUNT(*) FROM test_function
UNION ALL SELECT 'test_function_case', COUNT(*) FROM test_function_case
UNION ALL SELECT 'test_function_module', COUNT(*) FROM test_function_module
UNION ALL SELECT 'test_function_rely', COUNT(*) FROM test_function_rely
UNION ALL SELECT 'test_function_step', COUNT(*) FROM test_function_step
UNION ALL SELECT 'test_plan', COUNT(*) FROM test_plan
UNION ALL SELECT 'test_suite', COUNT(*) FROM test_suite
UNION ALL SELECT 'operation_log', COUNT(*) FROM operation_log
UNION ALL SELECT 'device', COUNT(*) FROM device;"
