import java.util.ArrayList;

public class Tag {
    private String name;
    private String description;
    private ArrayList<String> questionIds;

    public Tag(String name) {
        this.name = name;
    }

    public Tag(String name, String description) {
        this.name = name;
        this.description = description;
    }

    public void updateDescription(String description) {
        this.description = description;
    }

    public ArrayList<String> getQuestionIds() {
        return questionIds;
    }
}
