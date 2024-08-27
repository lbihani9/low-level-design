import java.time.Instant;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.UUID;

public class Question {
    public static enum QuestionStatus {
        ACTIVE,
        CLOSED
    }

    private String id;
    private String title;
    private String description;
    private String questionedBy;
    private ArrayList<Answer> answers;
    private ArrayList<String> tagNames;
    private Bounty bounty;
    private QuestionStatus status;

    public Question(String title, String description, String questionedBy, ArrayList<String> tagNames) {
        this.id = UUID.randomUUID().toString().substring(0, 8);
        this.title = title;
        this.description = description;
        this.questionedBy = questionedBy;
        this.answers = new ArrayList<>();
        this.tagNames = tagNames;
        this.bounty = null;
        this.status = QuestionStatus.ACTIVE;
    }

    public Question(String title, String description, String questionedBy) {
        this(title, description, questionedBy, new ArrayList<>());
    }

    public String getId() {
        return id;
    }

    public void addAnswer(String description, String username) {
        if (isClosed()) {
            throw new IllegalStateException("New answers cannot be accepted. Question is already closed.");
        }
        Answer answer = new Answer(description, username);
        answers.add(answer);
    }

    public boolean isClosed() {
        return this.status == QuestionStatus.CLOSED;
    }

    public void addBounty(int offeredReputations, Instant expiresAt, String createdBy) {
        if (isClosed()) {
            throw new IllegalStateException("Bounty cannot be added. Question is already closed");
        }
        this.bounty = new Bounty(offeredReputations, expiresAt, createdBy);
    }

    public Answer getAnswerById(String answerId) {
        for (Answer answer : answers) {
            if (answer.getId().equals(answerId)) {
                return answer;
            }
        }
        return null;
    }

    public ArrayList<Answer> getAnswers() {
        return answers;
    }

    public void close() {
        this.status = QuestionStatus.CLOSED;
    }
}
