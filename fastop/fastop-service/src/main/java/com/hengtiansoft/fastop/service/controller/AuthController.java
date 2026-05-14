package com.hengtiansoft.fastop.service.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.oauth2.core.oidc.user.OidcUser;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Collections;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

/**
 * 认证相关端点。
 */
@RestController
public class AuthController {

    /**
     * 返回当前已登录用户的基本信息及角色。前端通过此接口判断登录态。
     * 未认证的请求会被 SecurityConfig 的 authenticationEntryPoint 拦截，返回 401。
     */
    @GetMapping("/me")
    public ResponseEntity<Map<String, Object>> me(@AuthenticationPrincipal OidcUser oidcUser) {
        Map<String, Object> body = new LinkedHashMap<>();
        String username = oidcUser.getPreferredUsername();
        if (username == null || username.isEmpty()) {
            username = oidcUser.getSubject();
        }
        body.put("username", username);
        body.put("sub", oidcUser.getSubject());
        body.put("name", oidcUser.getFullName());
        body.put("email", oidcUser.getEmail());
        Object roleNames = oidcUser.getClaim("role_name");
        body.put("roles", roleNames instanceof List ? roleNames : Collections.emptyList());
        return ResponseEntity.ok(body);
    }

    /** 始终返回 401，供前端在白名单路由下探测未登录状态（不触发 OAuth2 跳转）。 */
    @GetMapping("/me/anonymous")
    public ResponseEntity<Map<String, Object>> anonymousProbe() {
        Map<String, Object> body = new HashMap<>();
        body.put("code", 401);
        body.put("data", null);
        body.put("message", "unauthenticated");
        return ResponseEntity.status(401).body(body);
    }
}
