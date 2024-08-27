import java.util.HashMap;
import java.util.Map;

public class TagManager {
    private Map<String, Tag> tags;
    private static TagManager instance;

    private TagManager() {
        tags = new HashMap<>();
    }

    public static TagManager getInstance() {
        if (instance == null) {
            synchronized (TagManager.class) {
                if(instance == null) {
                    instance = new TagManager();
                }
            }
        }
        return instance;
    }

    public Tag getTagByName(String name) {
        return tags.get(name);
    }

    public Tag createTagIfAbsent(String tagName) {
        if (!tags.containsKey(tagName)) {
            tags.put(tagName, new Tag(tagName));
        }
        return tags.get(tagName);
    }
}
