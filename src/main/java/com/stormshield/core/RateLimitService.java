package com.stormshield.core;

import org.springframework.stereotype.Service;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

@Service
public class RateLimitService {
    private final Map<String, TokenBucket> buckets = new ConcurrentHashMap<>();

    public boolean allowRequest(String apiKey) {
        TokenBucket bucket = buckets.computeIfAbsent(apiKey,
                k -> new TokenBucket(100, 100, 60_000));
        return bucket.tryConsume(1);
    }
}
