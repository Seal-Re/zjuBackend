package com.hengtiansoft.fastop.service.config;

import com.hengtiansoft.fastop.base.common.context.UserContextHolder;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.mock.web.MockHttpServletRequest;
import org.springframework.mock.web.MockHttpServletResponse;
import org.springframework.web.client.RestTemplate;

import java.util.concurrent.atomic.AtomicInteger;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.*;

/**
 * UserContextInterceptor 行为契约：
 *  - 解析 Authorization Bearer token，调 /userinfo 拿 username 写入 ThreadLocal
 *  - 命中 token→user 缓存（5min TTL）跳过 RTT，避免每请求都打 auth 服务
 *  - afterCompletion 清理 ThreadLocal，防线程池场景串号
 *  - 无 Authorization 头 / 非 Bearer / token 解析失败 仍返 true（不强制鉴权但不污染上下文）
 */
class UserContextInterceptorTest {

    private RestTemplate restTemplate;
    private UserContextInterceptor interceptor;

    @BeforeEach
    void setUp() {
        restTemplate = mock(RestTemplate.class);
        interceptor = new UserContextInterceptor(restTemplate, "http://localhost:5000");
    }

    @AfterEach
    void cleanup() {
        UserContextHolder.clear();
    }

    @Test
    void preHandleSetsUsernameFromUserinfo() {
        when(restTemplate.exchange(eq("http://localhost:5000/userinfo"), eq(HttpMethod.GET), any(HttpEntity.class), eq(String.class)))
                .thenReturn(new ResponseEntity<>(
                        "{\"code\":200,\"message\":\"ok\",\"data\":{\"username\":\"alice\",\"id\":\"u1\"}}",
                        HttpStatus.OK));

        MockHttpServletRequest req = new MockHttpServletRequest();
        req.addHeader("Authorization", "Bearer test-token-1");
        boolean ok = interceptor.preHandle(req, new MockHttpServletResponse(), null);

        assertTrue(ok);
        assertEquals("alice", UserContextHolder.getCurrentUser());
    }

    @Test
    void cachedTokenSkipsSecondHttpCall() {
        when(restTemplate.exchange(any(String.class), any(HttpMethod.class), any(HttpEntity.class), eq(String.class)))
                .thenReturn(new ResponseEntity<>(
                        "{\"code\":200,\"message\":\"ok\",\"data\":{\"username\":\"bob\"}}",
                        HttpStatus.OK));

        MockHttpServletRequest req1 = new MockHttpServletRequest();
        req1.addHeader("Authorization", "Bearer cached-token");
        interceptor.preHandle(req1, new MockHttpServletResponse(), null);
        interceptor.afterCompletion(req1, new MockHttpServletResponse(), null, null);

        MockHttpServletRequest req2 = new MockHttpServletRequest();
        req2.addHeader("Authorization", "Bearer cached-token");
        interceptor.preHandle(req2, new MockHttpServletResponse(), null);

        assertEquals("bob", UserContextHolder.getCurrentUser());
        verify(restTemplate, times(1))
                .exchange(any(String.class), any(HttpMethod.class), any(HttpEntity.class), eq(String.class));
    }

    @Test
    void afterCompletionClearsThreadLocal() {
        UserContextHolder.setCurrentUser("dirty");
        interceptor.afterCompletion(new MockHttpServletRequest(), new MockHttpServletResponse(), null, null);
        assertEquals("unknown", UserContextHolder.getCurrentUser());
    }

    @Test
    void noAuthHeaderStillReturnsTrue() {
        boolean ok = interceptor.preHandle(new MockHttpServletRequest(), new MockHttpServletResponse(), null);
        assertTrue(ok);
        // 无 Authorization 时不应触发 HTTP 调用
        verifyNoInteractions(restTemplate);
    }

    @Test
    void nonBearerTokenIgnored() {
        MockHttpServletRequest req = new MockHttpServletRequest();
        req.addHeader("Authorization", "Basic dXNlcjpwYXNz");
        boolean ok = interceptor.preHandle(req, new MockHttpServletResponse(), null);
        assertTrue(ok);
        verifyNoInteractions(restTemplate);
    }

    @Test
    void userinfoFailureDoesNotPropagate() {
        when(restTemplate.exchange(any(String.class), any(HttpMethod.class), any(HttpEntity.class), eq(String.class)))
                .thenThrow(new RuntimeException("auth service down"));

        MockHttpServletRequest req = new MockHttpServletRequest();
        req.addHeader("Authorization", "Bearer dead-token");

        // auth 服务挂掉时拦截器不应让请求 500，只是上下文留空
        AtomicInteger counter = new AtomicInteger();
        boolean ok = assertDoesNotThrow(() -> {
            counter.incrementAndGet();
            return interceptor.preHandle(req, new MockHttpServletResponse(), null);
        });
        assertTrue(ok);
        assertEquals("unknown", UserContextHolder.getCurrentUser());
    }
}
