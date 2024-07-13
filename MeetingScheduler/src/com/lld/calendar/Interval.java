package com.lld.calendar;

import java.time.Instant;
import java.util.UUID;

public class Interval {
    private String id;
    private Instant start;
    private Instant end;

    public Interval(Instant start, Instant end) {
        this.id = UUID.randomUUID().toString().substring(0, 8);
        this.start = start;
        this.end = end;
    }
}
