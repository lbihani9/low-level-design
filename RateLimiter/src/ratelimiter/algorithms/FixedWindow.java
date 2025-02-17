package ratelimiter.algorithms;

import ratelimiter.logs.FixedWindowLog;
import ratelimiter.logs.Log;
import ratelimiter.settings.Settings;

import java.time.Instant;
import java.util.concurrent.ConcurrentHashMap;

public class FixedWindow implements RateLimitingStrategy {
    private final ConcurrentHashMap<String, Log> requestLogs;
    private final Settings settings;

    public FixedWindow(Settings settings) {
        this.requestLogs = new ConcurrentHashMap<>();
        this.settings = settings;
    }

    @Override
    public boolean shouldRateLimit(String userId) {
        FixedWindowLog logEntry = (FixedWindowLog) requestLogs.compute(userId, (_, value) -> {
            if (value == null) {
                return new FixedWindowLog(settings);
            }

            FixedWindowLog log = (FixedWindowLog) value;
            if (log.expiresAt < Instant.now().toEpochMilli()) {
                return new FixedWindowLog(settings);
            }

            if (log.consumedSoFar <= settings.getRequestLimit()) {
                log.consume();
            }

            return log;
        });

//        System.out.println(logEntry);
        return logEntry.consumedSoFar > settings.getRequestLimit();
    }

    public Settings getSettings() {
        return settings;
    }
}
