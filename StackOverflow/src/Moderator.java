public class Moderator extends Member {

    public Moderator(String username, String name) {
        super(username, name);
    }

    public void closeQuestion(String questionId) {
        QuestionManager questionMgr = QuestionManager.getInstance();
        Question question = questionMgr.getQuestionById(questionId);
        if (question == null) {
            throw new IllegalStateException("Question not found");
        }
        question.close();
    }
}
