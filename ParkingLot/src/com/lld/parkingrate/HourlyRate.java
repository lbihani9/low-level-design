package com.lld.parkingrate;

import java.time.Duration;

public class HourlyRate implements ParkingRate {
    private double flatRate;

    public HourlyRate(double flatRate) {
        this.flatRate = flatRate;
    }

    @Override
    public double calculate(Duration d) {
        return flatRate * d.toHours();
    }
}
