package com.example.ErrorHandlingAndExceptionManagement.config;

import com.example.ErrorHandlingAndExceptionManagement.interceptor.ErrorRateLimitingInterceptor;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
@RequiredArgsConstructor
public class WebConfig implements WebMvcConfigurer {
    private final ErrorRateLimitingInterceptor errorRateLimitingInterceptor;

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(errorRateLimitingInterceptor)
                .addPathPatterns("/**");
    }
}
