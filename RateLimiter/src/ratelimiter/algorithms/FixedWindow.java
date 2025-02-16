package ratelimiter.algorithms;

import ratelimiter.log.FixedWindowLog;
import ratelimiter.log.Log;

import java.time.Instant;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.TimeUnit;

public class FixedWindow implements RateLimitingStrategy {
    public ConcurrentHashMap<String, Log> requestLogs;
    private final TimeUnit timeUnit;
    public final long limitPerTimeUnit;

    public FixedWindow(TimeUnit timeUnit, long limitPerTimeUnit) {
        this.requestLogs = new ConcurrentHashMap<>();
        this.timeUnit = timeUnit;
        this.limitPerTimeUnit = limitPerTimeUnit;
    }

    @Override
    public boolean shouldRateLimit(String userId) {
        FixedWindowLog logEntry = (FixedWindowLog) requestLogs.compute(userId, (key, value) -> {
            if (value == null) {
                return new FixedWindowLog(Instant.now().plus(limitPerTimeUnit, timeUnit.toChronoUnit()).toEpochMilli());
            }

            FixedWindowLog log = (FixedWindowLog) value;
            if (log.expiresAt >= Instant.now().toEpochMilli()) {
                if (log.consumedSoFar <= limitPerTimeUnit) {
                    log.consumeOneMore();
                }
            } else {
                log.reset(Instant.now().plus(limitPerTimeUnit, timeUnit.toChronoUnit()).toEpochMilli());
            }
            return value;
        });
        return logEntry.consumedSoFar > limitPerTimeUnit;
    }
}
