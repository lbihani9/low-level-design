package com.lld.gates;

import com.lld.payments.Payment;

import java.util.UUID;

public class Exit {
    private final String id;

    public Exit() {
        this.id = UUID.randomUUID().toString();
    }

    public String getId() {
        return id;
    }

    public Boolean processPayment(Payment payment) {
        return payment.processPayment();
    }
}
