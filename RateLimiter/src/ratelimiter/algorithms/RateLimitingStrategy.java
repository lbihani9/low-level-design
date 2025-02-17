package ratelimiter.algorithms;

import ratelimiter.settings.Settings;

public interface RateLimitingStrategy {
    boolean shouldRateLimit(String userId);
    Settings getSettings();
}
