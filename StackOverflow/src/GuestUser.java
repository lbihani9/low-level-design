import java.util.ArrayList;

public class GuestUser {
    private Search catalogue;

    public GuestUser(Search catalogue) {
        this.catalogue = catalogue;
    }

    public ArrayList<Question> searchQuestions(String query) {
        query = query.toLowerCase();
        if (query.startsWith("[tag]:")) {
            return catalogue.findQuestionsByTag(query.substring(6));
        }
        if (query.startsWith("[user]:")) {
            return catalogue.findQuestionsByUsername(query.substring(7));
        }
        return new ArrayList<>();
    }
}
