package com.hengtiansoft.fastop.service.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.Map;

/**
 * 前端用来判断是否需要跳转 Idp 登录的探测端点。
 */
@RestController
public class AuthController {

    @GetMapping("/me/anonymous")
    public ResponseEntity<Map<String, Object>> anonymousProbe() {
        Map<String, Object> body = new HashMap<>();
        body.put("code", 401);
        body.put("data", null);
        body.put("message", "unauthenticated");
        return ResponseEntity.status(401).body(body);
    }
}
