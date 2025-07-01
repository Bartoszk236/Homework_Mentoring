package com.example.JsonViewsAndConditionalSerialization.contorller;

import com.example.JsonViewsAndConditionalSerialization.service.ProfileService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.json.MappingJacksonValue;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/profile")
@RequiredArgsConstructor
public class ProfileController {
    private final ProfileService profileService;

    @GetMapping("/{profileId}")
    public ResponseEntity<MappingJacksonValue> getProfile(
            @PathVariable Long profileId,
            @RequestHeader("X-User-Role") String role
    ) {
        return ResponseEntity.status(HttpStatus.OK).body(profileService.getProfile(profileId, role));
    }
}
