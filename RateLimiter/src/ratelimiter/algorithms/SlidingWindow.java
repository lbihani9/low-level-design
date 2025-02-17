package ratelimiter.algorithms;

import ratelimiter.logs.Log;
import ratelimiter.settings.Settings;

import java.util.concurrent.ConcurrentHashMap;

public class SlidingWindow implements RateLimitingStrategy {
    public ConcurrentHashMap<String, Log> requestLogs;
    private final Settings settings;

    public SlidingWindow(Settings settings) {
        this.requestLogs = new ConcurrentHashMap<>();
        this.settings = settings;
    }

    @Override
    public boolean shouldRateLimit(String userId) {
        return false;
    }

    @Override
    public Settings getSettings() {
        return settings;
    }
}
