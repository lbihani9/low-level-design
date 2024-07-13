package com.lld.meeting;

import com.lld.calendar.Interval;
import com.lld.meetingroom.MeetingRoom;
import com.lld.user.User;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class Meeting {
    public enum MeetingState {
        COMPLETED,
        CANCELLED,
        SCHEDULED,
        ROOM_ASSIGNMENT_PENDING
    }
    private final String id;
    private String title;
    private final User organizer;
    private final List<User> participants;
    private int participantCount;
    private final Interval interval;
    private MeetingRoom room;
    private MeetingState state;

    public Meeting(String title, User organizer, Interval interval, int count) {
        this.id = UUID.randomUUID().toString().substring(0, 8);
        this.title = title;
        this.organizer = organizer;
        this.participantCount = count;
        this.interval = interval;
        this.participants = new ArrayList<>();
        this.state = MeetingState.ROOM_ASSIGNMENT_PENDING;
    }

    public String getId() {
        return id;
    }

    public void setMeetingRoom(MeetingRoom room) throws IllegalStateException {
        if (this.room != null) {
            throw new IllegalStateException("A room has already been assigned to this meeting.");
        }
        if (this.state == MeetingState.CANCELLED) {
            throw new IllegalStateException("This meeting has already been cancelled.");
        }
        this.room = room;
        this.state = MeetingState.SCHEDULED;
    }

    public void unsetMeetingRoom() {
        this.room = null;
        this.state = MeetingState.CANCELLED;
    }

    public Interval getInterval() {
        return interval;
    }

    public MeetingRoom getAssignedRoom() {
        return room;
    }

    public int getParticipantCount() {
        return participantCount;
    }

    public void addParticipant(User user) {
        Boolean isPresent = false;
        for (User u : participants) {
            if (u.getEmail().equals(user.getEmail())) {
                isPresent = true;
                break;
            }
        }
        if (!isPresent) {
            participants.add(user);
        }
    }

    public void removeParticipant(User user) {
        for (User u : participants) {
            if (u.getEmail().equals(user.getEmail())) {
                participants.remove(u);
                return;
            }
        }
    }
}
