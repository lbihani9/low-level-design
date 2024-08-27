import java.util.ArrayList;
import java.util.Map;

public class Member implements Observer extends User {
    private Account account;
    private ArrayList<Notification> notifications;
    private int reputations;
    private ArrayList<Question> questions;
    private Search catalogue;

    public Member(Account account, Search catalogue) {
        this.account = account;
        this.notifications = new ArrayList<>();
        this.reputations = 0;
        this.questions = new ArrayList<>();
        this.catalogue = catalogue;
    }

    @Override
    public void update(Notification notification) {
        notifications.add(notification);
    }

    public String getAccountId() {
        return account.getId();
    }

    void createQuestion(String title, String description, ArrayList<Tag> tags) {
        Question question = new Question(title, description, this, tags);
        questions.add(question);
    }

    void createAnswer(Question question, String content) {
        if (question.isClosed()) {
            throw new IllegalArgumentException("New answers cannot be accepted. Question is already closed");
        }
        Answer answer = new Answer(content, this, question.getId());
        question.addAnswer(answer);
    }

    ArrayList<Notification> getNotifications() {
        return notifications;
    }

    ArrayList<Question> getQuestions() {
        return questions;
    }

    public ArrayList<Question> searchQuestions(String query) {
        if (query.startsWith("[tag]:")) {
            return catalogue.findQuestionsByTag(query.substring(6));
        }
        if (query.startsWith("[user]:")) {
            return catalogue.findQuestionsByUsername(query.substring(7));
        }
        return new ArrayList<>();
    }
}
