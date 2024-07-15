package com.lld.meetingscheduler;

import com.lld.user.User;

import java.util.*;

public class MeetingScheduler {
    private String id;
    private TreeMap<Integer, List<MeetingRoom>> rooms;

    private static MeetingScheduler meetingScheduler;

    private MeetingScheduler() {
        this.id = UUID.randomUUID().toString().substring(0, 8);
        this.rooms = new TreeMap<>();
    }

    public void createRoom(String roomName, int capacity) {
        MeetingRoom room = new MeetingRoom(roomName, capacity);
        if (!rooms.containsKey(capacity)) {
            rooms.put(capacity, new ArrayList<>());
        }
        rooms.get(capacity).add(room);
    }

    public static synchronized MeetingScheduler getInstance() {
        if (meetingScheduler == null) {
            meetingScheduler = new MeetingScheduler();
        }
        return meetingScheduler;
    }

    public Meeting scheduleMeeting(int participantsCount, Interval interval, String title, User organizer) {
        if (rooms.ceilingKey(participantsCount) == null) {
            return null;
        }

        Meeting meeting = new Meeting(title, organizer, interval, participantsCount);

        int minRoomSize = participantsCount;
        while (rooms.ceilingKey(minRoomSize) != null && rooms.ceilingKey(minRoomSize) != minRoomSize) {
            minRoomSize = rooms.ceilingKey(minRoomSize);
            for (MeetingRoom room : rooms.get(minRoomSize)) {
                try {
                    Boolean ok = room.addToCalendarIfAvailable(meeting, interval);
                    if (ok) {
                        meeting.setMeetingRoom(room);
                        return meeting;
                    }
                } catch (IllegalStateException err) {
                    System.out.println(err.getMessage());
                    return null;
                }
            }
        }
        return null;
    }

    public void cancelMeeting(Meeting meeting) {
        MeetingRoom room = meeting.getAssignedRoom();
        if (room != null) {
            this.releaseRoom(room, meeting.getInterval());
        }
        meeting.unsetMeetingRoom();
    }

    public void addParticipant(Meeting meeting, User participant) {
        meeting.addParticipant(participant);
    }

    public void removeParticipant(Meeting meeting, User participant) {
        meeting.removeParticipant(participant);
    }

    private void releaseRoom(MeetingRoom room, Interval interval) {
        room.removeInterval(interval);
    }
}
