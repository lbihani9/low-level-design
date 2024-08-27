import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class Main {
    public static void main(String[] args) {
        // Initializing all the managers.
        AccountManager accountManager = AccountManager.getInstance();
        TagManager tagManager = TagManager.getInstance();
        NotificationManager notificationManager = NotificationManager.getInstance();
        QuestionManager questionManager = QuestionManager.getInstance();

        // User and guest creation
        Guest guest1 = new Guest();
        Guest guest2 = new Guest();
        Account moderatorAccount = new Account("lbihani9", "abc@gmail.com", "vsecret", "Lokesh Bihani", Account.AccountType.MODERATOR);
        Account memberAccount = new Account("username1", "xyz@gmail.com", "vvvsecret", "User name", Account.AccountType.MEMBER);

        Moderator moderator = (Moderator) moderatorAccount.getUser();
        Member member = (Member) memberAccount.getUser();

        // creating questions
        ArrayList<Question> allQuestions = new ArrayList<>();

        allQuestions.add(member.createQuestion("Q1", "Q1 description"));
        allQuestions.add(member.createQuestion("Q2", "Q2 description"));
        allQuestions.add(moderator.createQuestion("Q3", "Q3 description"));
        allQuestions.add(member.createQuestion("Q4", "Q4 description", new ArrayList<>(List.of("Java"))));

        // creating answers
        member.createAnswer(allQuestions.getFirst().getId(), "Answer 1");
        member.createAnswer(allQuestions.getFirst().getId(), "Answer 2");

        // creating comments
        try {
            member.createComment(allQuestions.getFirst().getId(), allQuestions.getFirst().getAnswers().getFirst().getId(), "comment 1");
            member.createComment("1", "1", "comment 2");
            member.createComment(allQuestions.getFirst().getId(), "2", "comment 3");
        } catch (Exception err) {
            System.out.println(err.getMessage());
        }
    }
}
