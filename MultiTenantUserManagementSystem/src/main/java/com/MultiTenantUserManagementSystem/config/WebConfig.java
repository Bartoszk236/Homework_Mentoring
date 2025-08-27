package com.MultiTenantUserManagementSystem.config;

import com.MultiTenantUserManagementSystem.interceptor.AuditInterceptor;
import com.MultiTenantUserManagementSystem.interceptor.RateLimitInterceptor;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
@RequiredArgsConstructor
public class WebConfig implements WebMvcConfigurer {
    private final AuditInterceptor auditInterceptor;
    private final RateLimitInterceptor rateLimitInterceptor;

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(auditInterceptor).order(1)
                .addPathPatterns("/**");
        registry.addInterceptor(rateLimitInterceptor).order(2)
                .addPathPatterns("/**");
    }
}
