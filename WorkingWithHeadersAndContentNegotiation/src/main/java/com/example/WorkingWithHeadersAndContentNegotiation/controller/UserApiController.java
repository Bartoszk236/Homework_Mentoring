package com.example.WorkingWithHeadersAndContentNegotiation.controller;

import com.example.WorkingWithHeadersAndContentNegotiation.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class UserApiController {
    private final UserService userService;

    @GetMapping(value = "/api/user/{id}", produces = {MediaType.APPLICATION_JSON_VALUE, MediaType.APPLICATION_XML_VALUE})
    public ResponseEntity<?> getUserById(
            @PathVariable("id") Long id,
            @RequestHeader(value = "Accept") String type,
            @RequestHeader(value = "X-API-Version") String apiVersion,
            @RequestHeader(value = "Authorization", required = false) String authorization,
            @RequestHeader(value = "If-None-Match", required = false) String ifNoneMatch
    ) {
        String etag = userService.getUserEtag(id);

        if (etag.equals(ifNoneMatch)) {
            return ResponseEntity.status(HttpStatus.NOT_MODIFIED)
                    .eTag(etag)
                    .header("X-Data-Source", "CACHE")
                    .build();
        }

        var result = userService.getUserById(id, type, apiVersion, authorization, ifNoneMatch);

        return ResponseEntity.status(HttpStatus.OK)
                .eTag(etag)
                .header("X-Data-Source", "DATABASE")
                .body(result);
    }
}
