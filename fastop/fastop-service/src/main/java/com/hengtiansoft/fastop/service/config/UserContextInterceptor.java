package com.hengtiansoft.fastop.service.config;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.hengtiansoft.fastop.base.common.context.UserContextHolder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.*;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.servlet.HandlerInterceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * 从请求的 Authorization: Bearer {token} 头中提取用户信息，
 * 调用认证服务 /userinfo 端点解析出用户名，存入 UserContextHolder。
 *
 * 性能优化：每个请求若都同步打 /userinfo 会引入一次 RTT，吞吐被 auth 服务拖累。
 * 这里加一个轻量内存缓存（token → username, TTL=5min），命中即直接放行。
 */
public class UserContextInterceptor implements HandlerInterceptor {

    private static final Logger LOG = LoggerFactory.getLogger(UserContextInterceptor.class);
    private static final long CACHE_TTL_MS = 5 * 60 * 1000L; // 5 分钟
    private static final int CACHE_MAX_SIZE = 5000;

    private final RestTemplate restTemplate;
    private final String authServiceUrl;
    private final Map<String, CachedUser> tokenCache = new ConcurrentHashMap<>();

    public UserContextInterceptor(RestTemplate restTemplate, String authServiceUrl) {
        this.restTemplate = restTemplate;
        this.authServiceUrl = authServiceUrl;
    }

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) {
        String authHeader = request.getHeader("Authorization");
        if (authHeader == null || !authHeader.startsWith("Bearer ")) {
            return true;
        }
        String token = authHeader.substring(7).trim();
        if (token.isEmpty()) {
            return true;
        }

        // 缓存命中
        CachedUser cached = tokenCache.get(token);
        long now = System.currentTimeMillis();
        if (cached != null && now - cached.timestamp < CACHE_TTL_MS) {
            UserContextHolder.setCurrentUser(cached.username);
            return true;
        }

        // 缓存未命中：同步打 /userinfo
        try {
            HttpHeaders headers = new HttpHeaders();
            headers.set("Authorization", authHeader);
            HttpEntity<Void> entity = new HttpEntity<>(headers);
            ResponseEntity<String> resp = restTemplate.exchange(
                    authServiceUrl + "/userinfo",
                    HttpMethod.GET,
                    entity,
                    String.class
            );
            if (resp.getStatusCode() == HttpStatus.OK && resp.getBody() != null) {
                JSONObject body = JSON.parseObject(resp.getBody());
                if (body.getIntValue("code") == 200) {
                    JSONObject data = body.getJSONObject("data");
                    if (data != null) {
                        String username = data.getString("username");
                        if (username != null) {
                            UserContextHolder.setCurrentUser(username);
                            putCache(token, username, now);
                        }
                    }
                }
            }
        } catch (Exception e) {
            LOG.debug("用户上下文解析忽略: {}", e.getMessage());
        }
        return true;
    }

    private void putCache(String token, String username, long now) {
        // 简易 size 控制：超过上限时清理过期项；仍不够则清空一半（O(n) 但避免无界增长）
        if (tokenCache.size() >= CACHE_MAX_SIZE) {
            tokenCache.entrySet().removeIf(e -> now - e.getValue().timestamp >= CACHE_TTL_MS);
            if (tokenCache.size() >= CACHE_MAX_SIZE) {
                int removed = 0;
                int target = CACHE_MAX_SIZE / 2;
                for (String key : tokenCache.keySet()) {
                    if (removed >= target) break;
                    tokenCache.remove(key);
                    removed++;
                }
            }
        }
        tokenCache.put(token, new CachedUser(username, now));
    }

    @Override
    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex) {
        UserContextHolder.clear();
    }

    private static final class CachedUser {
        final String username;
        final long timestamp;
        CachedUser(String username, long timestamp) {
            this.username = username;
            this.timestamp = timestamp;
        }
    }
}
