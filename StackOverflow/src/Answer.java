import java.time.Instant;
import java.util.ArrayList;

public class Answer implements Subject {
    private String questionId;
    private String content;
    private Instant createdAt;
    private Member createdBy;
    private int upvoteCounts;
    private int downvoteCounts;
    private int flagCounts;
    private boolean isAccepted;
    private ArrayList<Comment> comments;
    private ArrayList<Member> observers;

    public Answer(String content, Member createdBy, String questionId) {
        this.questionId = questionId;
        this.content = content;
        this.createdAt = Instant.now();
        this.createdBy = createdBy;
        this.upvoteCounts = 0;
        this.downvoteCounts = 0;
        this.flagCounts = 0;
        this.isAccepted = false;
        this.comments = new ArrayList<>();
        this.observers = new ArrayList<>();
        subscribe(createdBy);
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

    public void upvoteAnswer() {
        upvoteCounts++;
        Notification notification = new Notification("You have new upvote on your answer");
        notifyObservers(notification);
    }

    public void downvoteAnswer() {
        downvoteCounts++;
        Notification notification = new Notification("You have new downvote on your answer");
        notifyObservers(notification);
    }

    public void flagAnswer() {
        flagCounts++;
    }

    public void markAsAccepted() {
        isAccepted = true;
        Notification notification = new Notification("Your answer has been accepted by the author of the question.");
        notifyObservers(notification);
    }

    @Override
    public String toString() {
        return "Answer [questionId=" + questionId + ", content=" + content + ", createdAt=" + createdAt + "]";
    }
}
