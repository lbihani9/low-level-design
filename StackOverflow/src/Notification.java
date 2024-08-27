public class Notification {
    private String content;

    public Notification(String content) {
        this.content = content;
    }

    @Override
    public String toString() {
        return content;
    }
}
