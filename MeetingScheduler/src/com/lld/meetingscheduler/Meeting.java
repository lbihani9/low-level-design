package com.lld.meetingscheduler;

import com.lld.notification.Notification;
import com.lld.user.User;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class Meeting {
    public enum MeetingState {
        CANCELLED,
        SCHEDULED,
        ROOM_ASSIGNMENT_PENDING
    }
    private String id;
    private String title;
    private User organizer;
    private List<User> participants;
    private int participantCount;
    private Interval interval;
    private MeetingRoom room;
    private MeetingState state;

    Meeting(String title, User organizer, Interval interval, int count) {
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

    public String getTitle() {
        return title;
    }

    synchronized void setMeetingRoom(MeetingRoom room) throws IllegalStateException {
        if (this.room != null) {
            throw new IllegalStateException("A room has already been assigned to this meeting.");
        }
        if (this.state == MeetingState.CANCELLED) {
            throw new IllegalStateException("This meeting has already been cancelled.");
        }
        this.room = room;
        this.state = MeetingState.SCHEDULED;
    }

    synchronized void unsetMeetingRoom() {
        boolean shouldInformParticipant = this.room != null;
        this.room = null;
        this.state = MeetingState.CANCELLED;
        if (shouldInformParticipant) {
            for (User participant : participants) {
                participant.inform(new Notification("Meeting has been cancelled."));
            }
        }
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

    boolean addParticipant(User user) throws IllegalStateException {
        if (participantCount + 1 < room.getCapacity()) {
            throw new IllegalStateException("Meeting room is full.");
        }

        boolean isPresent = false;
        for (User u : participants) {
            if (u.getEmail().equals(user.getEmail())) {
                isPresent = true;
                break;
            }
        }

        if (!isPresent) {
            participants.add(user);
            participantCount += 1;
            String invitationMessage = "You've been invited to a meeting about " + this.title + " on " + this.interval.getReadableStartTime() + " in room number " + this.room.getRoomName();
            user.inform(new Notification(invitationMessage));
        }
        return !isPresent;
    }

    boolean removeParticipant(User user) {
        for (User u : participants) {
            if (u.getEmail().equals(user.getEmail())) {
                participants.remove(u);
                participantCount -= 1;
                String removalMessage = "You've been removed from the meeting " + this.title;
                user.inform(new Notification(removalMessage));
                return true;
            }
        }
        return false;
    }
}
