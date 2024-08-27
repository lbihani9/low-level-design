import java.util.ArrayList;

public class Search {
    private static Search instance;

    private Search() {}

    public static Search getInstance() {
        if (instance == null) {
            synchronized (Search.class) {
                if (instance == null) {
                    instance = new Search();
                }
            }
        }
        return instance;
    }

    public ArrayList<Question> findByTag(String tagName) {
        TagManager tagMgr = TagManager.getInstance();
        Tag tag = tagMgr.getTagByName(tagName);
        if (tag == null) {
            return null;
        }

        ArrayList<Question> questions = new ArrayList<>();
        QuestionManager questionMgr = QuestionManager.getInstance();

        for (String questionId: tag.getQuestionIds()) {
            Question question = questionMgr.getQuestionById(questionId);
            if (question != null) {
                questions.add(question);
            }
        }

        return questions;
    }

    public ArrayList<Question> findByUsername(String username) {
        AccountManager accountMgr = AccountManager.getInstance();
        Account account = accountMgr.getByUsername(username);
        if (account == null) {
            return null;
        }

        Member user = account.getUser();
        ArrayList<Question> questions = new ArrayList<>();
        QuestionManager questionMgr = QuestionManager.getInstance();

        for (String questionId: user.getQuestionIds()) {
            Question question = questionMgr.getQuestionById(questionId);
            if (question != null) {
                questions.add(question);
            }
        }
        return questions;
    }
}
