package ratelimiter.logs;

import ratelimiter.settings.Settings;

import java.time.Instant;

public class FixedWindowLog implements Log {
    public long expiresAt;
    public long consumedSoFar;

    public FixedWindowLog(Settings settings) {
        this(Instant.now().plus(settings.getTicks(), settings.getTimeUnit().toChronoUnit()).toEpochMilli());
    }

    public FixedWindowLog(long expiresAt) {
        this.consumedSoFar = 1;
        this.expiresAt = expiresAt;
    }

    public void consume() {
        consumedSoFar++;
    }

    @Override
    public String toString() {
        return "Expires at: " + expiresAt + ", Consumed: " + consumedSoFar;
    }
}
