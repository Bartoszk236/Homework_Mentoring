package com.example.ErrorHandlingAndExceptionManagement.interceptor;

import com.example.ErrorHandlingAndExceptionManagement.model.ErrorInfo;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import org.springframework.context.MessageSource;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

@Component
@RequiredArgsConstructor
@Getter
@Setter
public class ErrorRateLimitingInterceptor implements HandlerInterceptor {
    private static final int MAX_RETRIES = 5;
    private static final long BRAKE_IN_MILLIS = 1 * 60_000;
    private Map<String, ErrorInfo> errorRateLimitingMap = new ConcurrentHashMap<>();
    private final MessageSource messageSource;

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        String key = request.getRemoteAddr();
        ErrorInfo errorInfo = errorRateLimitingMap.get(key);

        if (errorInfo != null) {
            if (System.currentTimeMillis() - errorInfo.getLastUpdateTime() > BRAKE_IN_MILLIS) {
                errorRateLimitingMap.remove(key);
            } else if (errorInfo.getCounter().get() >= MAX_RETRIES) {
                response.setStatus(HttpStatus.TOO_MANY_REQUESTS.value());
                response.setContentType(MediaType.APPLICATION_JSON_VALUE);
                String code = "interceptor.too_many_request";
                String message = messageSource.getMessage(code, null, request.getLocale());
                response.getWriter().write("{\"message\":\"" + message + "\"}");
                return false;
            }
        }
        return true;
    }

    @Override
    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex) throws Exception {
        int status = response.getStatus();
        if (status >= 400 && status < 500) {
            String key = request.getRemoteAddr();
            ErrorInfo info = errorRateLimitingMap.computeIfAbsent(key, k -> new ErrorInfo());
            info.getCounter().incrementAndGet();
            info.setLastUpdateTime(System.currentTimeMillis());
        }
    }
}
