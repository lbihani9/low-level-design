package com.lld.display;

import com.lld.parkingspot.ParkingSpot;

import java.util.HashMap;
import java.util.Map;

public class DisplayScreen {
    private Map<ParkingSpot.SpotType, Integer> freeSpots;

    public DisplayScreen() {
        this.freeSpots = new HashMap<>();
    }

    public void display() {
        for (ParkingSpot.SpotType spotType : freeSpots.keySet()) {
            System.out.println(spotType + ": " + freeSpots.get(spotType));
        }
    }

    public void updateFreeSpots(ParkingSpot.SpotType spotType, int free) {
        freeSpots.put(spotType, free);
    }
}
