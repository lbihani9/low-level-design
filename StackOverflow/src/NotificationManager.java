import java.util.HashMap;
import java.util.Map;
import java.util.TreeSet;

public class NotificationManager {
    private static NotificationManager instance;
    private Map<String, TreeSet<String>> questionObservers;
    private Map<String, TreeSet<String>> answerObservers;

    private NotificationManager() {
        questionObservers = new HashMap<>();
        answerObservers = new HashMap<>();
    }

    public static NotificationManager getInstance() {
        if (instance == null) {
            synchronized (NotificationManager.class) {
                if (instance == null) {
                    instance = new NotificationManager();
                }
            }
        }
        return instance;
    }

    public void subscribeToQuestion(String questionId, String username) {
        if (!questionObservers.containsKey(questionId)) {
            questionObservers.put(questionId, new TreeSet<>());
        }
        questionObservers.get(questionId).add(username);
    }

    public void unsubscribeFromQuestion(String questionId, String username) {
        if (questionObservers.containsKey(questionId)) {
            questionObservers.get(questionId).remove(username);
        }
    }

    public void subscribeToAnswer(String answerId, String username) {
        if (!answerObservers.containsKey(answerId)) {
            answerObservers.put(answerId, new TreeSet<>());
        }
        answerObservers.get(answerId).add(username);
    }

    public void unsubscribeFromAnswer(String answerId, String username) {
        if (answerObservers.containsKey(answerId)) {
            answerObservers.get(answerId).remove(username);
        }
    }

    public void notifyForQuestion(String questionId, String message) {
        if (!questionObservers.containsKey(questionId)) {
            return;
        }

        AccountManager accountMgr = AccountManager.getInstance();
        for (String username : questionObservers.get(questionId)) {
            Account account = accountMgr.getByUsername(username);
            if (account.isActive()) {
                Member user = accountMgr.getByUsername(username).getUser();
                Notification notification = new Notification(message);
                user.pushNotification(notification);
            }
        }
    }

    public void notifyForAnswer(String answerId, String message) {
        if (!answerObservers.containsKey(answerId)) {
            return;
        }

        AccountManager accountMgr = AccountManager.getInstance();
        for (String username : answerObservers.get(answerId)) {
            Account account = accountMgr.getByUsername(username);
            if (account.isActive()) {
                Notification notification = new Notification(message);
                Member user = accountMgr.getByUsername(username).getUser();
                user.pushNotification(notification);
            }
        }
    }
}
