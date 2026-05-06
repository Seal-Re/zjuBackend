#!/bin/bash
# wrk2 HTTP load — measures QPS / P50 / P95 / P99.
# MSYS_NO_PATHCONV=1 needed for Git Bash on Windows so /scripts/... is not mangled.
export MSYS_NO_PATHCONV=1
set -euo pipefail
cd "$(dirname "$0")"

TOKEN=$(curl -s -X POST http://localhost:20002/oauth/token \
    -H "Content-Type: application/x-www-form-urlencoded" \
    -d "grant_type=password&username=admin&password=123456" \
    | python -c "import json,sys; print(json.load(sys.stdin)['data']['access_token'])")

echo "TOKEN=$TOKEN" >&2

# Inject token into lua script
TMP_LUA="/tmp/plan_list_tok.lua"
sed "s#__TOKEN__#$TOKEN#" plan_list.lua > "$TMP_LUA"
cp "$TMP_LUA" ./plan_list_run.lua

RESULTS_DIR="../results/wrk2"
mkdir -p "$RESULTS_DIR"
TS=$(date +%Y%m%dT%H%M%SZ)

for profile in smoke load stress; do
    case "$profile" in
        smoke)  T=2  C=10  D=30s  R=200  ;;
        load)   T=4  C=64  D=60s  R=1000 ;;
        stress) T=4  C=128 D=60s  R=2000 ;;
    esac
    echo "=== $profile: T=$T C=$C D=$D R=$R rps ==="
    docker run --rm \
        --add-host=host.docker.internal:host-gateway \
        -v "$(pwd):/scripts" \
        cylab/wrk2 \
        -t$T -c$C -d$D -R$R --latency \
        -s /scripts/plan_list_run.lua \
        http://host.docker.internal:20004 \
        2>&1 | tee "$RESULTS_DIR/${profile}-${TS}.txt"
done
rm -f ./plan_list_run.lua
echo "Reports: $RESULTS_DIR/"
