package com.example.dispatcher.server.data.flow;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.NonNull;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.UUID;

@Component
@Slf4j
public class CustomRequestInterceptor implements HandlerInterceptor {
    @Override
    public boolean preHandle(@NonNull HttpServletRequest request, @NonNull HttpServletResponse response, @NonNull Object handler) {
        request.setAttribute("startTime", System.currentTimeMillis());
        HashMap<String, Object> map = new HashMap<>();
        map.put("timestamp", LocalDateTime.now().toString());
        map.put("method", request.getMethod());
        map.put("path", request.getRequestURI());
        map.put("ip", request.getRemoteAddr());
        log.info(map.toString());
        response.addHeader("X-Request-ID", UUID.randomUUID().toString());
        return !request.getRequestURL().toString().contains("blocked");
    }

    @Override
    public void afterCompletion(@NonNull HttpServletRequest request, @NonNull HttpServletResponse response, @NonNull Object handler, Exception ex) {
        long startTime = (long) request.getAttribute("startTime");
        long duration = System.currentTimeMillis() - startTime;
        log.info("Czas wykonania requestu: {} ms", duration);
    }
}
