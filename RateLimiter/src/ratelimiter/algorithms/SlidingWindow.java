package ratelimiter.algorithms;

public class SlidingWindow implements RateLimitingStrategy {
    @Override
    public boolean shouldRateLimit(String userId) {
        return false;
    }
}
