package ratelimiter.algorithms;

public interface RateLimitingStrategy {
    boolean shouldRateLimit(String userId);
}
