import java.util.ArrayList;

public class Tag {
    private String name;
    private String description;
    private ArrayList<Question> questions;

    Tag(String name, String description) {
        this.name = name;
        this.description = description;
    }

    public ArrayList<Question> getQuestions() {
        return questions;
    }

    public void addQuestion(Question question) {
        questions.add(question);
    }

    @Override
    public String toString() {
        return "Tag [name=" + name + ", description=" + description + "]";
    }
}
