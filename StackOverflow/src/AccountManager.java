import java.util.HashMap;
import java.util.Map;

public class AccountManager {
    private Map<String, Account> accounts;
    private static AccountManager instance;

    private AccountManager() {
        accounts = new HashMap<>();
    }

    public static AccountManager getInstance() {
        if (instance == null) {
            synchronized (AccountManager.class) {
                if(instance == null) {
                    instance = new AccountManager();
                }
            }
        }
        return instance;
    }

    public void addAccount(String username, Account account) {
        accounts.put(username, account);
    }

    public Account getByUsername(String username) {
        return accounts.get(username);
    }
}
