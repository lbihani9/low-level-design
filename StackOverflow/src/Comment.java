import java.time.Instant;

public class Comment {
    private String content;
    private Member createdBy;
    private Instant createdAt;
    private int upvoteCounts;
    private int flagCounts;

    public Comment(String content, Member createdBy) {
        this.content = content;
        this.createdBy = createdBy;
        this.createdAt = Instant.now();
        this.upvoteCounts = 0;
        this.flagCounts = 0;
    }

    public void upvoteComment() {
        upvoteCounts++;
    }

    public void flagComment() {
        flagCounts++;
    }

    @Override
    public String toString() {
        return "Comment [content=" + content + ", createdBy=" + createdBy + ", createdAt=" + createdAt + "]";
    }
}
