package com.example.StreamingAndPerformanceOptimization.contorller;

import com.example.StreamingAndPerformanceOptimization.facade.StreamFacade;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.mvc.method.annotation.StreamingResponseBody;

@RestController
@RequiredArgsConstructor
public class StreamController {
    private final StreamFacade streamFacade;

    // bez headera co 1000 rekordów, ponieważ są wysyłane raz i nie są aktualizowane
    @GetMapping(value = "/api/reports/users/export")
    public ResponseEntity<StreamingResponseBody> exportUsers(
            @RequestParam(required = false) String filter,
            @RequestParam(defaultValue = "1000") int batchSize,
            @RequestHeader(name = "Users-Range", required = false) String usersRange
            ) {
        return streamFacade.export(filter, batchSize, usersRange);
    }
}
