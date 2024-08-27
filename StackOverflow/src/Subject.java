public interface Subject {
    void subscribe(Member user);
    void unsubscribe(Member user);
    void notifyObservers(Notification notification);
}
