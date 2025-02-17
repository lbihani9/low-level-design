package ratelimiter.settings;

import java.util.concurrent.TimeUnit;

public class Settings {
    private final TimeUnit timeUnit;
    private final long ticks;
    private final long requestLimit;

    public Settings() {
        this(TimeUnit.SECONDS, 1, 10);
    }

    public Settings(TimeUnit timeUnit, long ticks, long requestLimit) {
        if (timeUnit == null) {
            throw new NullPointerException("TimeUnit cannot be null");
        }
        if (timeUnit == TimeUnit.MICROSECONDS || timeUnit == TimeUnit.NANOSECONDS) {
            throw new IllegalArgumentException("Microseconds and Nanoseconds granularity is not supported");
        }
        this.timeUnit = timeUnit;
        this.ticks = ticks;
        this.requestLimit = requestLimit;
    }

    public TimeUnit getTimeUnit() {
        return timeUnit;
    }

    public long getTicks() {
        return ticks;
    }

    public long getRequestLimit() {
        return requestLimit;
    }

    @Override
    public String toString() {
        return "Settings: [timeUnit=" + timeUnit + ", ticks=" + ticks + ", requestLimit=" + requestLimit + "]";
    }
}
