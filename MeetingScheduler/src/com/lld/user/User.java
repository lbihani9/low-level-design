package com.lld.user;

import com.lld.calendar.Interval;
import com.lld.meeting.Meeting;
import com.lld.meetingscheduler.MeetingScheduler;

import java.time.Instant;
import java.util.LinkedList;
import java.util.List;

public class User {
    private String email;
    private List<Meeting> organizedMeetings = new LinkedList<>();

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

    public void addParticipant(Meeting meeting, User user) {
        meeting.addParticipant(user);
    }

    // TODO: Implement accept and reject invite methods.
}
