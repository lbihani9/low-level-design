package com.lld.account;

import com.lld.gates.Entrance;
import com.lld.gates.Exit;
import com.lld.parkinglot.ParkingLot;
import com.lld.parkingrate.HourlyRate;
import com.lld.parkingspot.ParkingSpot;

public class Admin extends Account {

    public Admin(String name, String password, String email) {
        super(name, password, email);
    }

    public Boolean addEntranceGate(ParkingLot parkingLot) {
        Entrance entrance = new Entrance();
        return parkingLot.addEntranceGate(entrance);
    }

    public Boolean addExitGate(ParkingLot parkingLot) {
        Exit exit = new Exit();
        return parkingLot.addExitGate(exit);
    }

    public Boolean addParkingSpot(ParkingLot parkingLot, ParkingSpot.SpotType type) {
        ParkingSpot spot = new ParkingSpot(type);
        return parkingLot.addParkingSpot(spot);
    }

    public Boolean removeParkingSpot(ParkingLot parkingLot, String parkingSpotId) {
        return parkingLot.removeParkingSpot(parkingSpotId);
    }

    public ParkingAgent createParkingAgent(String name, String email) {
        String DEFAULT_PASSWORD = "default";
        return new ParkingAgent(name, DEFAULT_PASSWORD, email);
    }

    public void setParkingRate(ParkingLot parkingLot, int rate) {
        parkingLot.setParkingRate(new HourlyRate(rate));
    }

    public ParkingLot createParkingLot(String name, int capacity, double flatRate) {
        return ParkingLot.getParkingLot(name, capacity, flatRate);
    }
}
