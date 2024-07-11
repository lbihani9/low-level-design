package com.lld.vehicle;

public class Vehicle {
    public enum VehicleType {
        CAR,
        TRUCK,
        VAN,
        MOTORCYCLE
    }

    private String vehicleNumber;
    private VehicleType type;

    public Vehicle(String vehicleNumber, VehicleType type) {
        this.vehicleNumber = vehicleNumber;
        this.type = type;
    }

    public String getVehicleNumber() {
        return vehicleNumber;
    }

    public VehicleType getType() {
        return type;
    }
}
