package com.lld.calendar;

import com.lld.meeting.Meeting;

import java.util.HashMap;
import java.util.Map;
import java.util.TreeSet;
import java.util.UUID;

public class Calendar {
    private static class Schedule {
        // TODO: Add comparator for intervals
        private final TreeSet<Interval> schedules = new TreeSet<>();

        public Boolean add(Interval interval) {
            // TODO: Implement logic to find conflicts. Return false in case of conflict.
            schedules.add(interval);
            return true;
        }

        public Boolean remove(Interval interval) {
            return schedules.remove(interval);
        }
    }

    private String id;
    private final Schedule schedules;
    private final Map<String, Meeting> scheduledMeetings = new HashMap<>();

    public Calendar() {
        this.id = UUID.randomUUID().toString().substring(0, 8);
        this.schedules = new Schedule();
    }

    public Boolean addToSchedule(Interval interval) {
        Boolean ok = false;
        synchronized (schedules) {
            ok = schedules.add(interval);
        }
        return ok;
    }

    public Boolean removeFromSchedule(Interval interval) {
        Boolean ok = false;
        synchronized (schedules) {
            ok = schedules.remove(interval);
        }
        return ok;
    }
}
