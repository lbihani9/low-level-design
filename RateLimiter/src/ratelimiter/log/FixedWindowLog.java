package ratelimiter.log;

public class FixedWindowLog implements Log {
    public long expiresAt;
    public long consumedSoFar;

    public FixedWindowLog(long expiresAt) {
        this.consumedSoFar = 1;
        this.expiresAt = expiresAt;
    }

    public void reset(long expiresAt) {
        this.consumedSoFar = 1;
        this.expiresAt = expiresAt;
    }

    public void consumeOneMore() {
        consumedSoFar++;
    }

    @Override
    public String toString() {
        return "Expires at: " + expiresAt + ", Consumed: " + consumedSoFar;
    }
}
