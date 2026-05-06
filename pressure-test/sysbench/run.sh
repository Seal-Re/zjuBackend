#!/bin/bash
# MySQL sysbench — write throughput / P95 on a sysbench-prepared schema in fastop-mysql.
# We use a dedicated DB `sysbench_pt` to avoid polluting autosys_1014.
export MSYS_NO_PATHCONV=1
set -euo pipefail
cd "$(dirname "$0")"

DB=sysbench_pt
HOST=host.docker.internal
PORT=20001
USER=root
PASS=Fastop@123

# Create sysbench DB
docker exec fastop-mysql mysql -u$USER -p$PASS -e "CREATE DATABASE IF NOT EXISTS $DB;" 2>/dev/null

RESULTS_DIR="../results/sysbench"
mkdir -p "$RESULTS_DIR"
TS=$(date +%Y%m%dT%H%M%SZ)

run_sysbench() {
    local script=$1
    local action=$2
    local extra=$3
    docker run --rm --add-host=$HOST:host-gateway severalnines/sysbench \
        sysbench \
        --db-driver=mysql \
        --mysql-host=$HOST --mysql-port=$PORT \
        --mysql-user=$USER --mysql-password=$PASS \
        --mysql-db=$DB \
        --tables=4 --table-size=100000 \
        --threads=8 --time=30 --report-interval=5 \
        $extra \
        "$script" "$action"
}

# Prepare
echo "=== prepare ==="
run_sysbench oltp_write_only prepare "" 2>&1 | tee "$RESULTS_DIR/prepare-$TS.txt" | tail -10

# Write-only workload
echo "=== oltp_write_only run ==="
run_sysbench oltp_write_only run "" 2>&1 | tee "$RESULTS_DIR/write_only-$TS.txt" | tail -25

# Insert workload
echo "=== oltp_insert run ==="
run_sysbench oltp_insert run "" 2>&1 | tee "$RESULTS_DIR/insert-$TS.txt" | tail -25

# Read-write mixed
echo "=== oltp_read_write run ==="
run_sysbench oltp_read_write run "" 2>&1 | tee "$RESULTS_DIR/read_write-$TS.txt" | tail -25

# Cleanup
echo "=== cleanup ==="
run_sysbench oltp_write_only cleanup "" 2>&1 | tail -5

echo "Reports: $RESULTS_DIR/"
