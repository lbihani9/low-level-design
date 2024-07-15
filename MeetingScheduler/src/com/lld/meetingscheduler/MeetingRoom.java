package com.lld.meetingscheduler;

public class MeetingRoom {
    private String roomName;
    private int capacity;
    private Calendar calendar;

    MeetingRoom(String roomName, int capacity) {
        this.roomName = roomName;
        this.capacity = capacity;
        this.calendar = new Calendar();
    }

    public String getRoomName() {
        return roomName;
    }

    Boolean addToCalendarIfAvailable(Meeting meeting, Interval interval) {
        return this.calendar.addToSchedule(meeting, interval);
    }

    Boolean removeInterval(Interval interval) {
        return this.calendar.removeFromSchedule(interval);
    }

    int getCapacity() {
        return capacity;
    }
}
