import java.util.HashMap;
import java.util.Map;

public class TagManager {
    private static TagManager instance;
    private Map<String, Tag> tags;

    private TagManager() {
        tags = new HashMap<>();
    }

    public static TagManager getInstance() {
        if (instance == null) {
            synchronized (TagManager.class) {
                if (instance == null) {
                    instance = new TagManager();
                }
            }
        }
        return instance;
    }

    public Tag getTagByName(String name) {
        return tags.get(name);
    }

    public void createTagIfDoesntExist(String name, String description) {
        if (!tags.containsKey(name)) {
            tags.put(name, new Tag(name, description));
        }
    }
}
