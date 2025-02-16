package ratelimiter;

import ratelimiter.algorithms.FixedWindow;
import ratelimiter.algorithms.RateLimitingStrategy;

import java.util.concurrent.TimeUnit;

public class RateLimiter {
    RateLimitingStrategy rateLimiter;

    public RateLimiter() {
        this(new FixedWindow(TimeUnit.MINUTES, 100));
    }

    public RateLimiter(RateLimitingStrategy rateLimiter) {
        this.rateLimiter = rateLimiter;
    }

    public boolean canAccess(String userId) {
        return !rateLimiter.shouldRateLimit(userId);
    }
}
