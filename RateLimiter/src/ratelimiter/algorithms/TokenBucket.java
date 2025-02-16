package ratelimiter.algorithms;

public class TokenBucket implements RateLimitingStrategy {
    @Override
    public boolean shouldRateLimit(String userId) {
        return false;
    }
}
