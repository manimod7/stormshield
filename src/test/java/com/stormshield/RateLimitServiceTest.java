package com.stormshield;

import com.stormshield.core.RateLimitService;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class RateLimitServiceTest {

    @Test
    public void testAllowRequest() {
        RateLimitService service = new RateLimitService();
        for (int i = 0; i < 100; i++) {
            assertTrue(service.allowRequest("key"));
        }
        assertFalse(service.allowRequest("key"));
    }
}
