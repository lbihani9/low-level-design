package com.lld.meetingscheduler;

import java.util.*;

class Calendar {
    private static class Schedule {
        private final TreeSet<Interval> schedules = new TreeSet<>();

        Boolean add(Interval interval) {
            for (Interval i : schedules) {
                if (i.hasIntersection(interval)) {
                    System.out.println("A conflicting interval already exists.");
                    return false;
                }
            }
            schedules.add(interval);
            return true;
        }

        Boolean remove(Interval interval) {
            return schedules.remove(interval);
        }
    }

    private String id;
    private Schedule schedules;
    private Map<String, Meeting> scheduledMeetings = new HashMap<>();

    Calendar() {
        this.id = UUID.randomUUID().toString().substring(0, 8);
        this.schedules = new Schedule();
    }

     synchronized List<Meeting> getMeetings() {
        return new ArrayList<>(scheduledMeetings.values());
    }

    synchronized Boolean addToSchedule(Meeting meeting, Interval interval) {
        Boolean ok = false;
        ok = schedules.add(interval);
        if (ok) {
            scheduledMeetings.put(interval.getId(), meeting);
        }
        return ok;
    }

    synchronized Boolean removeFromSchedule(Interval interval) {
        Boolean ok = false;
        ok = schedules.remove(interval);
        if (ok) {
            scheduledMeetings.remove(interval.getId());
        }
        return ok;
    }
}
