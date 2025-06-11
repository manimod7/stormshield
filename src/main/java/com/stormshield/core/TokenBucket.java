package com.stormshield.core;

import java.time.Instant;
import java.util.concurrent.atomic.AtomicLong;

public class TokenBucket {
    private final long capacity;
    private final long refillTokens;
    private final long refillIntervalMillis;

    private AtomicLong availableTokens;
    private AtomicLong lastRefillTimestamp;

    public TokenBucket(long capacity, long refillTokens, long refillIntervalMillis) {
        this.capacity = capacity;
        this.refillTokens = refillTokens;
        this.refillIntervalMillis = refillIntervalMillis;
        this.availableTokens = new AtomicLong(capacity);
        this.lastRefillTimestamp = new AtomicLong(Instant.now().toEpochMilli());
    }

    public synchronized boolean tryConsume(long tokens) {
        refill();
        if (availableTokens.get() >= tokens) {
            availableTokens.addAndGet(-tokens);
            return true;
        }
        return false;
    }

    private void refill() {
        long now = Instant.now().toEpochMilli();
        long last = lastRefillTimestamp.get();
        long elapsed = now - last;
        if (elapsed > refillIntervalMillis) {
            long cycles = elapsed / refillIntervalMillis;
            long newTokens = cycles * refillTokens;
            long current = Math.min(capacity, availableTokens.get() + newTokens);
            availableTokens.set(current);
            lastRefillTimestamp.set(last + cycles * refillIntervalMillis);
        }
    }
}
