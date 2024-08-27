import java.time.Instant;

public class Bounty {
    private int offeredReputations;
    private Instant expiresAt;

    Bounty(int offeredReputations, Instant expiresAt) {
        this.offeredReputations = offeredReputations;
        this.expiresAt = expiresAt;
    }
}
