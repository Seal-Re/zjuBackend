package com.hengtiansoft.fastop.jmh;

import okhttp3.*;
import org.openjdk.jmh.annotations.*;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.TimeUnit;

/**
 * 鉴权侧两条路径微基准：
 *   feignHttp:  RestTemplate/OkHttp 调 auth-mock /userinfo（fastop 当前实际链路）
 *   jvmInProcess: 假设鉴权放进 JVM 内联调（直接 map.lookup，省网络栈）
 *
 * Token 已预存到本地缓存 (UserContextInterceptor 同款 5 min TTL)，缓存命中分支 vs 未命中（两次）。
 *
 * Run:
 *   mvn -f pressure-test/jmh/pom.xml clean package
 *   java -jar pressure-test/jmh/target/benchmarks.jar AuthBench -wi 3 -i 5 -f 1 -bm avgt -tu us
 */
@State(Scope.Benchmark)
@Warmup(iterations = 3, time = 1)
@Measurement(iterations = 5, time = 2)
@Fork(1)
@BenchmarkMode(Mode.AverageTime)
@OutputTimeUnit(TimeUnit.MICROSECONDS)
public class AuthBench {

    private static final String AUTH_URL = System.getenv().getOrDefault("AUTH_URL", "http://localhost:20002");
    private OkHttpClient http;
    private String token;
    private final Map<String, String> cache = new ConcurrentHashMap<>();

    @Setup
    public void setup() throws IOException {
        http = new OkHttpClient.Builder()
                .connectTimeout(5, TimeUnit.SECONDS)
                .readTimeout(5, TimeUnit.SECONDS)
                .build();

        // Login once to get a token
        RequestBody body = new FormBody.Builder()
                .add("grant_type", "password")
                .add("username", "admin")
                .add("password", "123456")
                .build();
        try (Response r = http.newCall(new Request.Builder()
                .url(AUTH_URL + "/oauth/token").post(body).build()).execute()) {
            String s = r.body().string();
            int i = s.indexOf("access_token") + 16;
            token = s.substring(i, s.indexOf("\"", i));
        }
        // Pre-fill cache for hit case
        cache.put(token, "admin");
    }

    @Benchmark
    public String feignHttpUserinfo() throws IOException {
        Request req = new Request.Builder()
                .url(AUTH_URL + "/userinfo")
                .header("Authorization", "Bearer " + token)
                .build();
        try (Response r = http.newCall(req).execute()) {
            return r.body().string();
        }
    }

    @Benchmark
    public String jvmInProcessLookup() {
        return cache.get(token);
    }

    @Benchmark
    public String feignWithCacheLayer() throws IOException {
        // Simulates UserContextInterceptor's cache-then-fallback pattern, hit case
        String hit = cache.get(token);
        if (hit != null) return hit;
        Request req = new Request.Builder()
                .url(AUTH_URL + "/userinfo")
                .header("Authorization", "Bearer " + token)
                .build();
        try (Response r = http.newCall(req).execute()) {
            String body = r.body().string();
            cache.put(token, "admin");
            return body;
        }
    }
}
