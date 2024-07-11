package com.lld.parkingspot;

import java.util.UUID;

public class ParkingSpot {

    public enum SpotType {
        COMPACT,
        LARGE,
        MOTORCYCLE
    }

    private String id;
    private SpotType type;
    private Boolean isFree;

    public ParkingSpot(SpotType type) {
        this.id = UUID.randomUUID().toString();
        this.type = type;
        this.isFree = true;
    }

    public Boolean isAvailable() {
        return isFree;
    }

    public void markUnavailable() {
        isFree = false;
    }

    public void markAvailable() {
        isFree = true;
    }

    public String getId() {
        return id;
    }

    public SpotType getType() {
        return type;
    }
}