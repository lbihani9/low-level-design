package com.lld.meetingscheduler;

import java.time.Instant;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.UUID;

public class Interval implements Comparable<Interval> {
    private String id;
    private Instant start;
    private Instant end;

    public Interval(Instant start, Instant end) {
        this.id = UUID.randomUUID().toString().substring(0, 8);
        this.start = start;
        this.end = end;
    }

    public String getId() {
        return id;
    }

    public String getReadableStartTime() {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss z");
        return start.atZone(ZoneId.systemDefault()).format(formatter);
    }

    public Boolean hasIntersection(Interval other) {
        return !(this.end.isBefore(other.getStart()) || this.start.isAfter(other.getEnd()));
    }

    public Instant getStart() {
        return start;
    }

    public Instant getEnd() {
        return end;
    }

    @Override
    public int compareTo(Interval o) {
        if (this.start.isBefore(o.start)) {
            return -1;
        } else if (this.start.isAfter(o.start)) {
            return 1;
        } else {
            return 0;
        }
    }
}
