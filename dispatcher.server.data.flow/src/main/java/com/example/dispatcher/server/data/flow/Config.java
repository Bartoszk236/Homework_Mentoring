package com.example.dispatcher.server.data.flow;

import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@RequiredArgsConstructor
@Configuration
public class Config implements WebMvcConfigurer {
    private final CustomRequestInterceptor customRequestInterceptor;

    @Override
    public void addInterceptors(@NonNull InterceptorRegistry registry) {
        registry.addInterceptor(customRequestInterceptor)
                .addPathPatterns("/**");
    }
}
