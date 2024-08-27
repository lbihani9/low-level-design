import java.time.Instant;

public class Bounty {
    private int reputations;
    private Instant expiresAt;
    private String createdBy;

    public Bounty(int reputations, Instant expiresAt, String createdBy) {
        this.reputations = reputations;
        this.expiresAt = expiresAt;
        this.createdBy = createdBy;
    }

    public void updateReputation(int reputations) {
        this.reputations = reputations;
    }

    public void updateExpiresAt(Instant expiresAt) {
        this.expiresAt = expiresAt;
    }
}
