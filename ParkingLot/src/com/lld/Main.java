package com.lld;

import com.lld.account.Admin;
import com.lld.account.ParkingAgent;
import com.lld.gates.Entrance;
import com.lld.gates.Exit;
import com.lld.parkinglot.ParkingLot;
import com.lld.parkingspot.ParkingSpot;
import com.lld.parkingticket.ParkingTicket;
import com.lld.payments.PaymentStrategy;
import com.lld.payments.UpiStratgey;
import com.lld.vehicle.Vehicle;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

public class Main {
    public static void main(String[] args) {
        Admin admin1 = new Admin("Lokesh Bihani", "secret", "lokeshbihani99@gmail.com");
        System.out.println("Admin id: " + admin1.getId());

        ParkingAgent agent1 = admin1.createParkingAgent("Alice", "alice@gmail.com");
        System.out.println("Agent id: " + agent1.getId());

        ParkingLot parkingLot = admin1.createParkingLot("Parking Lot 1", 40, 10);
        System.out.println("Parking lot id: " + parkingLot.getId());

        List<Exit> exits = new LinkedList<>();
        List<Entrance> entrances = new LinkedList<>();

        Entrance entrance = admin1.addEntranceGate(parkingLot);
        if (entrance != null) {
            entrances.add(entrance);
            System.out.println("Entrance Gate added");
        }

        Exit exit = admin1.addExitGate(parkingLot);
        if (exit != null) {
            exits.add(exit);
            System.out.println("Exiting Gate added");
        }

        Map<ParkingSpot.SpotType, Integer> spots = new HashMap<>();
        spots.put(ParkingSpot.SpotType.MOTORCYCLE, 20);
        spots.put(ParkingSpot.SpotType.COMPACT, 10);
        spots.put(ParkingSpot.SpotType.LARGE, 5);

        List<String> spotIds = new LinkedList<>();

        for (ParkingSpot.SpotType spotType : spots.keySet()) {
            for (int i=0; i<spots.get(spotType); i++) {
                String spotId = admin1.addParkingSpot(parkingLot, spotType);
                spotIds.add(spotId);
                System.out.println(spotType + " spot created successfully.");
            }
        }

        String vehicleNumber = "H123";
        Vehicle.VehicleType vehicleType = Vehicle.VehicleType.CAR;

        ParkingTicket ticket = agent1.parkVehicle(parkingLot, vehicleNumber, vehicleType);
        System.out.println("Ticket id: " + ticket.getId());
        System.out.println("Assigned spot id: " + ticket.getAssignedSpot().getId());

        Boolean wasSpotRemoved = admin1.removeParkingSpot(parkingLot, ticket.getAssignedSpot().getId());
        if (!wasSpotRemoved) {
            System.out.println("Failed to remove parking spot");
        }

        PaymentStrategy paymentStrategy = new UpiStratgey("123@axl");

        Boolean wasPaymentSuccessful = parkingLot.handleCheckout(ticket, exits.getLast(), paymentStrategy);
        if (wasPaymentSuccessful) {
            System.out.println("Payment Successful");
        } else {
            System.out.println("Payment Failed");
        }

        String toBeRemovedSpot = spotIds.removeLast();
        Boolean ok = admin1.removeParkingSpot(parkingLot, toBeRemovedSpot);
        if (ok) {
            System.out.println("Spot with id=" + toBeRemovedSpot + " removed successfully");
        }
    }
}
