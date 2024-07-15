import com.lld.meetingscheduler.Meeting;
import com.lld.meetingscheduler.MeetingRoom;
import com.lld.meetingscheduler.MeetingScheduler;
import com.lld.notification.Notification;
import com.lld.user.User;

import java.time.Instant;
import java.util.ArrayList;
import java.util.List;

public class Main {
    public static void main(String[] args) {
        MeetingScheduler scheduler = MeetingScheduler.getInstance();
        scheduler.createRoom("R1", 10);
        scheduler.createRoom("R2", 15);
        scheduler.createRoom("R3", 5);
        scheduler.createRoom("R4", 50);
        scheduler.createRoom("R5", 5);

        List<User> users = new ArrayList<>();
        users.add(new User("alice@gmail.com"));
        users.add(new User("bob@gmail.com"));

        User organizer = new User("lokeshbihani99@gmail.com");
        User organizer2 = new User("lbihani9@gmail.com");


        // Thread 1 and 2 have conflicting intervals.
        Thread thread1 = new Thread(() -> {
            Boolean ok = organizer.createMeeting(scheduler, 4, "Test meeting 1", Instant.now().plusSeconds(3600), Instant.now().plusSeconds(3 * 3600));
            if (ok) {
                System.out.println("First meeting created");
            }
        }, "Thread 1");

        Thread thread2 = new Thread(() -> {
            Boolean ok = organizer2.createMeeting(scheduler, 4, "Test meeting 2", Instant.now().plusSeconds(3600), Instant.now().plusSeconds(2 * 3600));
            if (ok) {
                System.out.println("Second meeting created");
            }
        }, "Thread 2");

        thread1.start();
        thread2.start();

        try {
            thread1.join();
            thread2.join();
        } catch (Exception e) {
            e.printStackTrace();
        }

        Boolean ok = organizer.createMeeting(scheduler, 4, "Test meeting 3", Instant.now().plusSeconds(4 * 3600), Instant.now().plusSeconds(5 * 3600));
        if (ok) {
            System.out.println("Third meeting created");
        }

        List<Meeting> myMeetings = organizer.getOrganizedMeetings();
        System.out.println(myMeetings);
        System.out.println(organizer2.getOrganizedMeetings());

//        for (User user : users) {
//            organizer.addParticipant(scheduler, myMeetings.getFirst(), user);
//            var notifications = user.getNotifications();
//            // Reading notifications
//            for (Notification notification : notifications) {
//                System.out.println("User(" + user.getEmail() + "): " + notification.getDescription());
//            }
//        }
//
//        organizer.cancelMeeting(scheduler, myMeetings.getFirst().getId());
//
//        System.out.println("Notifications after cancellation");
//
//        for (User user : users) {
//            organizer.addParticipant(scheduler, myMeetings.getFirst(), user);
//            var notifications = user.getNotifications();
//            // Reading notifications
//            for (Notification notification : notifications) {
//                System.out.println("User(" + user.getEmail() + "): " + notification.getDescription());
//            }
//        }
    }
}