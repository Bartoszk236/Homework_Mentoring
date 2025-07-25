package com.example.StreamingAndPerformanceOptimization.facade;

import com.example.StreamingAndPerformanceOptimization.praser.RangePraser;
import com.example.StreamingAndPerformanceOptimization.service.StreamService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.mvc.method.annotation.StreamingResponseBody;

import java.util.Map;

@Component
@RequiredArgsConstructor
@Slf4j
public class StreamFacade {
    private final StreamService streamService;
    private final RangePraser rangePraser;

    public ResponseEntity<StreamingResponseBody> export(String filter, int batchSize, String usersRange) {
        if (usersRange == null) {
            return ResponseEntity.status(HttpStatus.OK)
                    .header(HttpHeaders.CONTENT_ENCODING, "gzip")
                    .header(HttpHeaders.ACCEPT_RANGES, "users")
                    .body(streamService.stream(filter, batchSize, 0, Integer.MAX_VALUE));
        }

        Map<String, Integer> rangeValueMap = rangePraser.rangeToValue(usersRange);
        Integer start = rangeValueMap.get("start");
        Integer end = rangeValueMap.get("end");

        return ResponseEntity.status(HttpStatus.PARTIAL_CONTENT)
                .header(HttpHeaders.ACCEPT_RANGES, "users")
                .header(HttpHeaders.CONTENT_ENCODING, "gzip")
                .body(streamService.stream(filter, batchSize, start, end));
    }
}
