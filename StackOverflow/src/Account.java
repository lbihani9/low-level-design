import java.util.UUID;

public class Account {
    private String id;
    private String name;
    private String username;
    private String email;
    private String password;

    Account(String name, String username, String email, String password) {
        this.id = UUID.randomUUID().toString().substring(0, 8);
        this.name = name;
        this.username = username;
        this.email = email;
        this.password = password;
    }

    public String getId() {
        return id;
    }
}
