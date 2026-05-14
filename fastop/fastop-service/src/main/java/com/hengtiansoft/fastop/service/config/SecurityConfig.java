package com.hengtiansoft.fastop.service.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.logout.HttpStatusReturningLogoutSuccessHandler;
import org.springframework.security.web.csrf.CookieCsrfTokenRepository;

@Configuration
public class SecurityConfig {

    @Value("${fastop.web.post-login-redirect}")
    private String postLoginRedirect;

    @Bean
    SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
                .authorizeHttpRequests(auth -> auth
                        .antMatchers("/", "/error").permitAll()
                        .anyRequest().authenticated()
                )
                .oauth2Login(oauth2 -> oauth2
                        .successHandler((req, res, auth) -> res.sendRedirect(postLoginRedirect))
                )
                .oauth2Client(Customizer.withDefaults())
                // CSRF: 写 XSRF-TOKEN cookie（JS 可读），Axios 默认读此 cookie 并发送 X-XSRF-TOKEN 头
                .csrf(csrf -> csrf
                        .csrfTokenRepository(CookieCsrfTokenRepository.withHttpOnlyFalse())
                )
                .logout(logout -> logout
                        .logoutSuccessHandler(new HttpStatusReturningLogoutSuccessHandler(HttpStatus.NO_CONTENT))
                )
                // 未认证的 JSON 请求直接返回 401，避免 Axios 跟随重定向链拿到错误的 HTML
                .exceptionHandling(ex -> ex
                        .authenticationEntryPoint((request, response, e) -> {
                            String accept = request.getHeader(HttpHeaders.ACCEPT);
                            if (accept != null && accept.contains(MediaType.APPLICATION_JSON_VALUE)) {
                                response.setStatus(HttpStatus.UNAUTHORIZED.value());
                                response.setContentType(MediaType.APPLICATION_JSON_VALUE + ";charset=UTF-8");
                                response.getWriter().write("{\"code\":401,\"message\":\"Unauthenticated\"}");
                            } else {
                                response.sendRedirect(request.getContextPath() + "/oauth2/authorization/idp");
                            }
                        })
                );
        return http.build();
    }
}
