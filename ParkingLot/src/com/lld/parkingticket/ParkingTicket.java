package com.lld.parkingticket;

import com.lld.parkingspot.ParkingSpot;
import com.lld.payments.Payment;
import com.lld.vehicle.Vehicle;

import java.time.Duration;
import java.time.Instant;
import java.util.UUID;

public class ParkingTicket {
    public enum ParkingStatus {
        UNPAID,
        PAID
    }

    private final String id;
    private final Instant createdAt;
    private ParkingStatus status;
    private Instant checkedOutAt;
    private Vehicle vehicle;
    private ParkingSpot spot;
    private Payment payment;

    public ParkingTicket(Vehicle vehicle, ParkingSpot spot) {
        this.id = UUID.randomUUID().toString();
        this.createdAt = Instant.now();
        this.status = ParkingStatus.UNPAID;
        this.vehicle = vehicle;
        this.spot = spot;
    }

    public void setPayment(Payment payment) {
        this.payment = payment;
    }

    public ParkingSpot getAssignedSpot() {
        return spot;
    }

    public void markAsPaid() {
        this.status = ParkingStatus.PAID;
    }

    public String getId() {
        return id;
    }

    private void setCheckout() {
        if (checkedOutAt == null) {
            checkedOutAt = Instant.now();
        }
    }

    public Duration getParkingDuration() {
        setCheckout();
        return Duration.between(createdAt, checkedOutAt);
    }
}
