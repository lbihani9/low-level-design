package com.lld.meetingscheduler;

import java.util.*;

public class MeetingRoom {
    /**
     * This class is responsible for maintaining non overlapping meeting intervals.
     */
    private static class Schedule {
        private final TreeSet<Interval> intervals = new TreeSet<>();

        Boolean add(Interval interval) {
            for (Interval i : intervals) {
                if (i.hasIntersection(interval)) {
                    return false;
                }
            }
            intervals.add(interval);
            return true;
        }

        Boolean remove(Interval interval) {
            return intervals.remove(interval);
        }
    }

    /**
     * A meeting room has calendar and calendar stores "schedules", so other classes should ideally interact with
     * Calendar using `MeetingRoom` only.
     */
    private static class Calendar {

        private Schedule schedules;

        Calendar() {
            this.schedules = new Schedule();
        }

        Boolean addToSchedule(Interval interval) {
            return schedules.add(interval);
        }

        Boolean removeFromSchedule(Interval interval) {
            return schedules.remove(interval);
        }
    }

    private String roomName;
    private int capacity;
    private Map<String, Meeting> meetings = new HashMap<>();
    private Calendar calendar;

    MeetingRoom(String roomName, int capacity) {
        this.roomName = roomName;
        this.capacity = capacity;
        this.calendar = new Calendar();
    }

    public String getRoomName() {
        return roomName;
    }

    synchronized List<Meeting> getMeetings() {
        return new ArrayList<>(meetings.values());
    }

    synchronized Boolean addToCalendarIfAvailable(Meeting meeting, Interval interval) {
        Boolean ok = calendar.addToSchedule(interval);
        if (ok) {
            try {
                meeting.setMeetingRoom(this);
                meetings.put(interval.getId(), meeting);
            } catch (Exception e) {
                calendar.removeFromSchedule(interval);
                ok = false;
            }
        }
        return ok;
    }

    synchronized Boolean removeInterval(Interval interval) {
        Boolean ok = calendar.removeFromSchedule(interval);
        if (ok) {
            Meeting meeting = meetings.remove(interval.getId());
            meeting.unsetMeetingRoom();
        }
        return ok;
    }

    int getCapacity() {
        return capacity;
    }
}
