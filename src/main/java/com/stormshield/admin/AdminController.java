package com.stormshield.admin;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

@RestController
public class AdminController {

    @PostMapping("/plans")
    public ResponseEntity<String> createPlan(@RequestBody Map<String, Object> body) {
        // Placeholder implementation
        return ResponseEntity.ok("Plan created");
    }

    @PostMapping("/register-key")
    public ResponseEntity<String> registerKey(@RequestBody Map<String, Object> body) {
        // Placeholder implementation
        return ResponseEntity.ok("Key registered");
    }
}
