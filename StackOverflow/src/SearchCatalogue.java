import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class SearchCatalogue implements Search {
    private Map<String, Tag> tagMap;
    private Map<String, Member> memberMap;

    private static SearchCatalogue catalogue;

    private SearchCatalogue () {
        tagMap = new HashMap<>();
        memberMap = new HashMap<>();
    }

    public static SearchCatalogue getInstance() {
        if (catalogue == null) {
            catalogue = new SearchCatalogue();
        }
        return catalogue;
    }

    @Override
    public ArrayList<Question> findQuestionsByTag(String tagName) {
        if (!tagMap.containsKey(tagName)) {
            return new ArrayList<>();
        }

        Tag tag = tagMap.get(tagName);
        return tag.getQuestions();
    }

    @Override
    public ArrayList<Question> findQuestionsByUsername(String username) {
        if (!memberMap.containsKey(username)) {
            return new ArrayList<>();
        }
        Member member = memberMap.get(username);
        return member.getQuestions();
    }
}
