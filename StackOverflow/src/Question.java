import java.time.Instant;
import java.util.ArrayList;
import java.util.UUID;

public class Question implements Subject {
    public static int CLOSE_QUESTION_VOTE_THRESHOLD = 100;

    public static enum QuestionStatus {
        OPEN,
        CLOSED
    }

    private String id;
    private String title;
    private String description;
    private int upvoteCounts;
    private int downvoteCounts;
    private int flagCounts;
    private Bounty bounty;
    private ArrayList<Comment> comments;
    private ArrayList<Answer> answers;
    private ArrayList<Tag> tags;
    private Instant createdAt;
    private Member createdBy;
    private ArrayList<Member> observers;
    private QuestionStatus status;
    private int closureVoteCounts;

    public Question(String title, String description, Member createdBy, ArrayList<Tag> tags) {
        this.id = UUID.randomUUID().toString().substring(0, 8);
        this.title = title;
        this.description = description;
        this.createdAt = Instant.now();
        this.createdBy = createdBy;
        this.upvoteCounts = 0;
        this.downvoteCounts = 0;
        this.flagCounts = 0;
        this.answers = new ArrayList<>();
        this.tags = tags;
        this.observers = new ArrayList<>();
        this.comments = new ArrayList<>();
        this.status = QuestionStatus.OPEN;
        this.closureVoteCounts = 0;
        for (Tag tag : tags) {
            tag.addQuestion(this);
        }
        subscribe(createdBy);
    }

    public String getId() {
        return id;
    }

    @Override
    public void subscribe(Member user) {
        observers.add(user);
    }

    @Override
    public void unsubscribe(Member user) {
        for (int i = 0; i < observers.size(); i++) {
            if (user.getAccountId().equals(observers.get(i).getAccountId())) {
                observers.remove(i);
                break;
            }
        }
    }

    @Override
    public void notifyObservers(Notification notification) {
        for (Observer observer : observers) {
            observer.update(notification);
        }
    }

    public void upvote() {
        if (isClosed()) {
            throw new IllegalStateException("Question has already been closed.");
        }
        upvoteCounts++;
        Notification notification = new Notification("You have new upvote on your question.");
        notifyObservers(notification);
    }

    public void downvote() {
        if (isClosed()) {
            throw new IllegalStateException("Question has already been closed.");
        }
        downvoteCounts++;
        Notification notification = new Notification("You have new downvote on your question.");
        notifyObservers(notification);
    }

    public void flag() {
        flagCounts++;
    }

    public void voteForClosure() {
        if (isClosed()) {
            throw new IllegalStateException("Question has already been closed.");
        }
        closureVoteCounts++;
        if (closureVoteCounts > CLOSE_QUESTION_VOTE_THRESHOLD) {
            updateStatus(QuestionStatus.CLOSED);
        }
    }

    public void addAnswer(Answer answer) {
        if (isClosed()) {
            throw new IllegalStateException("Question has already been closed.");
        }
        answers.add(answer);
        Notification notification = new Notification("You have new answer on your question.");
        notifyObservers(notification);
    }

    public void addComment(Comment comment) {
        if (isClosed()) {
            throw new IllegalStateException("Question has already been closed.");
        }
        comments.add(comment);
    }

    public void addBounty(Bounty bounty) {
        if (isClosed()) {
            throw new IllegalStateException("Question has already been closed.");
        }
        if (this.bounty != null) {
            throw new IllegalStateException("A bounty already exists on this question.");
        }
        this.bounty = bounty;
    }

    boolean isClosed() {
        return status == QuestionStatus.CLOSED;
    }

    public void updateStatus(QuestionStatus status) {
        this.status = status;
    }

    @Override
    public String toString() {
        return "Question [id=" + id + ", title=" + title + ", description=" + description + ", upvoteCounts=" + upvoteCounts + ", downvoteCounts=" + downvoteCounts + ", status=" + status + ", closureVoteCounts=" + closureVoteCounts + "]";
    }
}
