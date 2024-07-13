package com.lld.meetingscheduler;

import com.lld.calendar.Interval;
import com.lld.meeting.Meeting;
import com.lld.meetingroom.MeetingRoom;
import com.lld.user.User;

import java.util.*;

public class MeetingScheduler {
    private String id;
    private final TreeMap<Integer, List<MeetingRoom>> rooms;

    private static MeetingScheduler meetingScheduler;

    private MeetingScheduler() {
        this.id = UUID.randomUUID().toString().substring(0, 8);
        this.rooms = new TreeMap<>();
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
        while (rooms.ceilingKey(minRoomSize) != null) {
            minRoomSize = rooms.ceilingKey(minRoomSize);
            for (MeetingRoom room : rooms.get(minRoomSize)) {
                try {
                    Boolean ok = room.addToCalendarIfAvailable(interval);
                    if (ok) {
                        meeting.setMeetingRoom(room);
                        return meeting;
                    }
                } catch (IllegalStateException err) {
                    System.out.println(err.toString());
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

    private void releaseRoom(MeetingRoom room, Interval interval) {
        room.removeInterval(interval);
    }
}
