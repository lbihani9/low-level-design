public class Moderator extends User {
    public Moderator(Account account) {
        super(account);
    }

    public void closeQuestion(Question question) {
        question.updateStatus(Question.QuestionStatus.CLOSED);
    }

    public void openQuestion(Question question) {
        question.updateStatus(Question.QuestionStatus.OPEN);
    }
}
