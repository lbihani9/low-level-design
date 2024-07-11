package com.lld.parkinglot;

import com.lld.display.DisplayScreen;
import com.lld.gates.Entrance;
import com.lld.gates.Exit;
import com.lld.parkingrate.HourlyRate;
import com.lld.parkingrate.ParkingRate;
import com.lld.parkingspot.ParkingSpot;
import com.lld.parkingticket.ParkingTicket;
import com.lld.payments.Payment;
import com.lld.payments.PaymentStrategy;
import com.lld.vehicle.Vehicle;

import java.time.Duration;
import java.util.*;

public class ParkingLot {
    private String id;
    private String address;
    private int capacity;
    private static ParkingLot parkingLot;
    private List<Entrance> entrances = new ArrayList<>();
    private List<Exit> exits = new ArrayList<>();
    private Map<ParkingSpot.SpotType, Map<String, ParkingSpot>> busySpots = new HashMap<>();
    private Map<ParkingSpot.SpotType, TreeMap<String, ParkingSpot>> availableSpots = new HashMap<>();
    private Map<String, ParkingTicket> tickets = new HashMap<>();
    private final Map<Vehicle.VehicleType, ParkingSpot.SpotType> vehicleToSpotCompatibility = new HashMap<>();
    private ParkingRate parkingRate;
    private DisplayScreen screen;

    private ParkingLot(String address, int capacity, double flatRate) {
        this.id = UUID.randomUUID().toString();
        this.address = address;
        this.capacity = capacity;
        this.parkingRate = new HourlyRate(flatRate);
        this.screen = new DisplayScreen();

        for (ParkingSpot.SpotType spotType : ParkingSpot.SpotType.values()) {
            availableSpots.put(spotType, new TreeMap<>());
            busySpots.put(spotType, new HashMap<>());
            this.screen.updateFreeSpots(spotType, 0);
        }

        vehicleToSpotCompatibility.put(Vehicle.VehicleType.MOTORCYCLE, ParkingSpot.SpotType.MOTORCYCLE);
        vehicleToSpotCompatibility.put(Vehicle.VehicleType.CAR, ParkingSpot.SpotType.COMPACT);
        vehicleToSpotCompatibility.put(Vehicle.VehicleType.TRUCK, ParkingSpot.SpotType.LARGE);
        vehicleToSpotCompatibility.put(Vehicle.VehicleType.VAN, ParkingSpot.SpotType.COMPACT);
    }

    public void setParkingRate(ParkingRate parkingRate) {
        this.parkingRate = parkingRate;
    }

    public static synchronized ParkingLot getParkingLot(String address, int capacity, double flatRate) {
        if (parkingLot == null) {
            parkingLot = new ParkingLot(address, capacity, flatRate);
        }
        return parkingLot;
    }

    private int getTotalCreatedSpots() {
        int totalSpots = 0;
        for (ParkingSpot.SpotType spotType : ParkingSpot.SpotType.values()) {
            totalSpots += availableSpots.get(spotType).size();
        }
        return totalSpots;
    }

    private Boolean hasAvailableSpots(ParkingSpot.SpotType spotType) {
        return !availableSpots.get(spotType).isEmpty();
    }

    public ParkingTicket getTicket(Vehicle vehicle) {
        ParkingSpot.SpotType spotType = vehicleToSpotCompatibility.get(vehicle.getType());
        if (!hasAvailableSpots(spotType)) {
            return null;
        }

        Map.Entry<String, ParkingSpot> firstSpot = availableSpots.get(spotType).pollFirstEntry();
        ParkingTicket ticket = new ParkingTicket(vehicle, firstSpot.getValue());

        busySpots.get(spotType).put(firstSpot.getKey(), firstSpot.getValue());
        tickets.put(ticket.getId(), ticket);

        this.screen.updateFreeSpots(spotType, availableSpots.get(spotType).size());
        this.screen.display();

        return ticket;
    }

    private Map<ParkingSpot.SpotType, Integer> getFreeSpotStats() {
        Map<ParkingSpot.SpotType, Integer> freeSpotStats = new HashMap<>();

        for (ParkingSpot.SpotType spotType : ParkingSpot.SpotType.values()) {
            freeSpotStats.put(spotType, availableSpots.get(spotType).size());
        }
        return freeSpotStats;
    }

    public Boolean addEntranceGate(Entrance entrance) {
        entrances.add(entrance);
        return true;
    }

    public Boolean addExitGate(Exit exitGate) {
        exits.add(exitGate);
        return true;
    }

    public Boolean handleCheckout(ParkingTicket ticket, Exit exitGate, PaymentStrategy paymentStrategy) {
        Duration duration = ticket.getParkingDuration();
        double fee = parkingRate.calculate(duration);

        Payment payment = new Payment(fee);
        payment.setPaymentStrategy(paymentStrategy);

        Boolean ok = exitGate.processPayment(payment);
        if (ok) {
            ticket.markAsPaid();
            ParkingSpot spot = ticket.getAssignedSpot();
            busySpots.get(spot.getType()).remove(spot.getId());
            availableSpots.get(spot.getType()).put(spot.getId(), spot);
            tickets.remove(ticket.getId());
        }
        return ok;
    }

    public Boolean addParkingSpot(ParkingSpot spot) {
        if (getTotalCreatedSpots() >= capacity) {
            // TODO: throw exception("Reached max capacity")
            return false;
        }
        availableSpots.get(spot.getType()).put(spot.getId(), spot);
        return true;
    }

    public Boolean removeParkingSpot(String id) {
        for (ParkingSpot.SpotType spotType : ParkingSpot.SpotType.values()) {
            if (availableSpots.get(spotType).containsKey(id)) {
                availableSpots.get(spotType).remove(id);
                return true;
            }
            if (busySpots.get(spotType).containsKey(id)) {
                // TODO: throw exception("This spot is already being used")
                return false;
            }
        }

        // TODO: throw exception("No spot found")
        return false;
    }

    public String getId() {
        return id;
    }

    public String getAddress() {
        return address;
    }

    public int getCapacity() {
        return capacity;
    }
}
