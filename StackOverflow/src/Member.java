import java.time.Instant;
import java.util.ArrayList;

public class Member {
    private String username;
    private String name;
    private ArrayList<String> questionIds;
    private ArrayList<Notification> notifications;

    public Member(String username, String name) {
        this.name = name;
        this.username = username;
        questionIds = new ArrayList<>();
        notifications = new ArrayList<>();
    }

    public Question createQuestion(String title, String description, ArrayList<String> tagNames) {
        TagManager tagMgr = TagManager.getInstance();
        for (String tagName : tagNames) {
            tagMgr.createTagIfAbsent(tagName);
        }

        Question question = new Question(title, description, this.username, tagNames);
        questionIds.add(question.getId());

        QuestionManager questionMgr = QuestionManager.getInstance();
        questionMgr.registerQuestion(question);
        return question;
    }

    public Question createQuestion(String title, String description) {
        return createQuestion(title, description, new ArrayList<>());
    }

    public void createAnswer(String questionId, String description) {
        QuestionManager questionMgr = QuestionManager.getInstance();
        Question question = questionMgr.getQuestionById(questionId);
        if (question == null) {
            throw new IllegalArgumentException("Question does not exist");
        }
        question.addAnswer(description, this.username);
    }

    public void createBounty(String questionId, int offeredReputations, Instant expiresAt) {
        QuestionManager questionMgr = QuestionManager.getInstance();
        Question question = questionMgr.getQuestionById(questionId);
        if (question == null) {
            throw new IllegalArgumentException("Question does not exist");
        }
        question.addBounty(offeredReputations, expiresAt, this.username);
    }

    public void createComment(String questionId, String answerId, String content) {
        QuestionManager questionMgr = QuestionManager.getInstance();
        Question question = questionMgr.getQuestionById(questionId);
        if (question == null) {
            throw new IllegalArgumentException("Question does not exist");
        }

        Answer answer = question.getAnswerById(answerId);
        if (answer == null) {
            throw new IllegalArgumentException("Answer does not exist");
        }
        answer.addComment(content, this.username);
    }

    public ArrayList<String> getQuestionIds() {
        return questionIds;
    }

    public void pushNotification(Notification notification) {
        notifications.add(notification);
    }

    public ArrayList<Notification> getNotifications() {
        return notifications;
    }

    public ArrayList<Question> search(String query) {
        Search searchMgr = Search.getInstance();

        if (query.startsWith("[tag]:")) {
            return searchMgr.findByTag(query.substring(6));
        }
        if (query.startsWith("[user]:")) {
            return searchMgr.findByUsername(query.substring(7));
        }
        return new ArrayList<>();
    }
}
