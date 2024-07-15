package com.lld.user;

import com.lld.meetingscheduler.Interval;
import com.lld.meetingscheduler.Meeting;
import com.lld.meetingscheduler.MeetingScheduler;
import com.lld.notification.Notification;

import java.time.Instant;
import java.util.LinkedList;
import java.util.List;

public class User {
    private String email;
    private List<Meeting> organizedMeetings = new LinkedList<>();
    private List<Notification> notifications = new LinkedList<>();

    public User(String email) {
        this.email = email;
    }

    public String getEmail() {
        return email;
    }

    public Boolean createMeeting(MeetingScheduler scheduler, int participantsCount, String title, Instant start, Instant end) {
        Interval interval = new Interval(start, end);
        Meeting meeting = scheduler.scheduleMeeting(participantsCount, interval, title, this);
        if (meeting != null) {
            organizedMeetings.add(meeting);
        }
        return meeting != null;
    }

    public void cancelMeeting(MeetingScheduler scheduler, String meetingId) {
        for (Meeting meeting : organizedMeetings) {
            if (meeting.getId().equals(meetingId)) {
                scheduler.cancelMeeting(meeting);
                organizedMeetings.remove(meeting);
                break;
            }
        }
    }

    public List<Notification> getNotifications() {
        return notifications;
    }

    public void addParticipant(MeetingScheduler scheduler, Meeting meeting, User user) {
        scheduler.addParticipant(meeting, user);
    }

    public void removeParticipant(MeetingScheduler scheduler, Meeting meeting, User user) {
        scheduler.removeParticipant(meeting, user);
    }

    public void inform(Notification notification) {
        notifications.add(notification);
    }

    public List<Meeting> getOrganizedMeetings() {
        return organizedMeetings;
    }
}
