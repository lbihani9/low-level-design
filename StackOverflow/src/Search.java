import java.util.ArrayList;

public interface Search {
    ArrayList<Question> findQuestionsByTag(String tag);
    ArrayList<Question> findQuestionsByUsername(String username);
}
