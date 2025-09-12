package com.example.LoggingWithLevelsJsonLogsAndMdc.interceptor;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.NonNull;
import org.slf4j.MDC;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;

@Component
public class MdcInterceptor implements HandlerInterceptor {
    @Override
    public void afterCompletion(@NonNull HttpServletRequest request,
                                @NonNull HttpServletResponse response, @NonNull Object handler, Exception ex) {
        MDC.clear();
    }
}
