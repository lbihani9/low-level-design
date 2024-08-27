import java.time.Instant;

public class Notification {
    private String content;
    private Instant createdAt;

    public Notification(String content) {
        this.content = content;
        this.createdAt = Instant.now();
    }
}
