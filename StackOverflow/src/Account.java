import java.util.UUID;

public class Account {
    public static enum AccountType {
        MEMBER,
        MODERATOR
    }

    public static enum AccountStatus {
        ACTIVE,
        CLOSED,
        BANNED
    }

    private String id;
    private String email;
    private String password;
    private Member user;
    private AccountStatus status;

    Account(String username, String email, String password, String name, AccountType type) {
        this.id = UUID.randomUUID().toString().substring(0, 8);
        this.email = email;
        this.password = password;
        this.status = AccountStatus.ACTIVE;

        // TODO: This is violating Open/Closed principle. Fix it.
        if (type == AccountType.MEMBER) {
            this.user = new Member(username, name);
        } else {
            this.user = new Moderator(username, name);
        }
    }

    public String getId() {
        return id;
    }

    public Member getUser() {
        return user;
    }

    public boolean isActive() {
        return status == AccountStatus.ACTIVE;
    }
}
