package ratelimiter;

import ratelimiter.algorithms.FixedWindow;
import ratelimiter.algorithms.RateLimitingStrategy;
import ratelimiter.settings.Settings;

import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class RateLimiter {
    private final RateLimitingStrategy rateLimiter;
    private final ExecutorService executor = Executors.newFixedThreadPool(8);

    public RateLimiter() {
        this(new Settings());
    }

    public RateLimiter(Settings settings) {
        this(new FixedWindow(settings));
    }

    public RateLimiter(RateLimitingStrategy rateLimiter) {
        this.rateLimiter = rateLimiter;
    }

    public boolean canAccess(String userId) throws ExecutionException, InterruptedException {
        return executor.submit(() -> !rateLimiter.shouldRateLimit(userId)).get();
    }

    public Settings getRateLimitSettings() {
        return rateLimiter.getSettings();
    }
}
