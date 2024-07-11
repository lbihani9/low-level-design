package com.lld.account;

import com.lld.parkinglot.ParkingLot;
import com.lld.parkingticket.ParkingTicket;
import com.lld.vehicle.Vehicle;

public class ParkingAgent extends Account {

    ParkingAgent(String name, String password, String email) {
        super(name, password, email);
    }

    public ParkingTicket parkVehicle(ParkingLot parkingLot, String vehicleNumber, Vehicle.VehicleType type) {
        Vehicle vehicle = new Vehicle(vehicleNumber, type);
        return parkingLot.getTicket(vehicle);
    }
}
