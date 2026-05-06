-- wrk2 plan list / function / suite / device read-mix
-- Token is injected by run.sh into __TOKEN__ placeholder before docker exec.
wrk.headers["Authorization"] = "Bearer __TOKEN__"
wrk.headers["Content-Type"] = "application/json"

local routes = {
    "/fastop/planner/plan/listAll",
    "/fastop/designer/testFunction/listAll",
    "/fastop/designer/testSuite/listAll",
    "/fastop/devices/list",
    "/fastop/base/listAllBaseStruct",
}

local r = 0
function request()
    r = r + 1
    return wrk.format("GET", routes[(r % #routes) + 1])
end

function done(summary, latency, requests)
    io.write(string.format("\nRequests/sec: %.2f\n", summary.requests / (summary.duration / 1e6)))
    io.write(string.format("p50 latency: %.2fms\n", latency:percentile(50) / 1000))
    io.write(string.format("p95 latency: %.2fms\n", latency:percentile(95) / 1000))
    io.write(string.format("p99 latency: %.2fms\n", latency:percentile(99) / 1000))
    io.write(string.format("max latency: %.2fms\n", latency.max / 1000))
end
