import java.util.HashMap;
import java.util.Map;

public class QuestionManager {
    private static QuestionManager instance;
    private Map<String, Question> questions;

    private QuestionManager() {
        questions = new HashMap<>();
    }

    public static QuestionManager getInstance() {
        if(instance == null) {
            synchronized(QuestionManager.class) {
                if(instance == null) {
                    instance = new QuestionManager();
                }
            }
        }
        return instance;
    }

    public Question getQuestionById(String id) {
        return questions.get(id);
    }

    public void registerQuestion(Question question) {
        questions.put(question.getId(), question);
    }
}
