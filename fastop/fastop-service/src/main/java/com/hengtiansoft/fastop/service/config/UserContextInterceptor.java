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

/**
 * 从请求的 Authorization: Bearer {token} 头中提取用户信息，
 * 调用认证服务 /userinfo 端点解析出用户名，存入 UserContextHolder。
 */
public class UserContextInterceptor implements HandlerInterceptor {

    private static final Logger LOG = LoggerFactory.getLogger(UserContextInterceptor.class);

    private final RestTemplate restTemplate;
    private final String authServiceUrl;

    public UserContextInterceptor(RestTemplate restTemplate, String authServiceUrl) {
        this.restTemplate = restTemplate;
        this.authServiceUrl = authServiceUrl;
    }

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) {
        String authHeader = request.getHeader("Authorization");
        if (authHeader != null && authHeader.startsWith("Bearer ")) {
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
                            }
                        }
                    }
                }
            } catch (Exception e) {
                LOG.debug("用户上下文解析忽略: {}", e.getMessage());
            }
        }
        return true;
    }

    @Override
    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex) {
        UserContextHolder.clear();
    }
}
