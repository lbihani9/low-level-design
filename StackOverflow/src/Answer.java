import java.util.ArrayList;
import java.util.UUID;

public class Answer {
    private String id;
    private String description;
    private String answeredBy;
    private ArrayList<Comment> comments;

    public Answer(String description, String answeredBy) {
        this.id = UUID.randomUUID().toString().substring(0, 8);
        this.description = description;
        this.answeredBy = answeredBy;
        this.comments = new ArrayList<>();
    }

    public void addComment(String content, String commentedBy) {
        Comment comment = new Comment(content, commentedBy);
        comments.add(comment);
    }

    public String getId() {
        return id;
    }
}
