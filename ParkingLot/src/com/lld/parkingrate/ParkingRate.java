package com.lld.parkingrate;

import java.time.Duration;

public interface ParkingRate {
    double calculate(Duration d);
}
