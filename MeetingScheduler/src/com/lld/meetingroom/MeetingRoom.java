package com.lld.meetingroom;

import com.lld.calendar.Calendar;
import com.lld.calendar.Interval;

public class MeetingRoom {
    private String roomName;
    private int capacity;
    private Calendar calendar;

    public MeetingRoom(String roomName, int capacity) {
        this.roomName = roomName;
        this.capacity = capacity;
        this.calendar = new Calendar();
    }

    public synchronized Boolean addToCalendarIfAvailable(Interval interval) {
        return this.calendar.addToSchedule(interval);
    }

    public synchronized Boolean removeInterval(Interval interval) {
        return this.calendar.removeFromSchedule(interval);
    }
}
