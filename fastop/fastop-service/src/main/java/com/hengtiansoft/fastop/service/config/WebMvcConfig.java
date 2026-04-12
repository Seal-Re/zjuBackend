package com.hengtiansoft.fastop.service.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class WebMvcConfig implements WebMvcConfigurer {

    @Autowired
    private RestTemplate restTemplate;

    @Value("${auth.service.url:http://localhost:5000}")
    private String authServiceUrl;

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(new UserContextInterceptor(restTemplate, authServiceUrl))
                .addPathPatterns("/**");
    }
}
