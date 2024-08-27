import java.util.ArrayList;

public class Guest {

    public ArrayList<Question> search(String query) {
        Search searchMgr = Search.getInstance();

        if (query.startsWith("[tag]:")) {
            return searchMgr.findByTag(query.substring(6));
        }
        if (query.startsWith("[user]:")) {
            return searchMgr.findByUsername(query.substring(7));
        }
        return new ArrayList<>();
    }
}
