package com.hengtiansoft.fastop.service.config;

import com.hengtiansoft.fastop.base.common.context.UserContextHolder;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.oauth2.core.oidc.user.OidcUser;
import org.springframework.web.servlet.HandlerInterceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * 从 Spring Security 上下文（OAuth2/OIDC 登录态）中提取当前用户名，写入 UserContextHolder，
 * 供下游业务代码通过 ThreadLocal 读取。请求结束后清理。
 */
public class UserContextInterceptor implements HandlerInterceptor {

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        if (auth != null && auth.isAuthenticated()) {
            String username = resolveUsername(auth);
            if (username != null) {
                UserContextHolder.setCurrentUser(username);
            }
        }
        return true;
    }

    @Override
    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex) {
        UserContextHolder.clear();
    }

    private String resolveUsername(Authentication auth) {
        Object principal = auth.getPrincipal();
        if (principal instanceof OidcUser) {
            OidcUser oidc = (OidcUser) principal;
            // 优先 preferred_username，其次 sub（Idp 把用户名放在 sub）
            String preferred = oidc.getPreferredUsername();
            if (preferred != null && !preferred.isEmpty()) {
                return preferred;
            }
            return oidc.getSubject();
        }
        return auth.getName();
    }
}
