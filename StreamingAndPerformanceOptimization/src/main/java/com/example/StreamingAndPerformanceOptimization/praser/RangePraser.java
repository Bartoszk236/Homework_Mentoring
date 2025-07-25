package com.example.StreamingAndPerformanceOptimization.praser;

import org.springframework.stereotype.Component;

import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Component
public class RangePraser {
    public Map<String, Integer> rangeToValue(String usersRange) {
        if (usersRange.startsWith("users=")) {
            String subStringRange = usersRange.substring("users=".length());

            Pattern p = Pattern.compile("^(\\d+)-(\\d+)$");
            Matcher m = p.matcher(subStringRange);
            if (!m.matches()) {
                throw new IllegalArgumentException("invalid users range format");
            }
            int start = Integer.parseInt(m.group(1));
            int end   = Integer.parseInt(m.group(2));

            if (start >= end)
                throw new IllegalArgumentException("end value cannot be less or equal than start value");
            return Map.of("start", start, "end", end);
        } else {
            throw new IllegalArgumentException("users range must start with 'users='");
        }
    }
}
