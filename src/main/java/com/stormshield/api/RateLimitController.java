package com.stormshield.api;

import com.stormshield.core.RateLimitService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class RateLimitController {

    private final RateLimitService rateLimitService;

    public RateLimitController(RateLimitService rateLimitService) {
        this.rateLimitService = rateLimitService;
    }

    @GetMapping("/check-limit")
    public ResponseEntity<String> checkLimit(@RequestHeader("X-API-KEY") String apiKey) {
        if (rateLimitService.allowRequest(apiKey)) {
            return ResponseEntity.ok("Allowed");
        } else {
            return ResponseEntity.status(HttpStatus.TOO_MANY_REQUESTS).body("Rate limit exceeded");
        }
    }
}
