package com.lld.payments;

import java.time.Instant;
import java.util.UUID;

public class Payment {

    public static enum PaymentType {
        UPI,
        DEBIT_CARD
    }

    public static enum PaymentStatus {
        FAILED,
        PENDING,
        SUCCESS
    }

    private String paymentId;
    private Instant date;
    private PaymentStatus status;
    private PaymentStrategy paymentStrategy;
    private double amount;

    public Payment(double amount) {
        this.paymentId = UUID.randomUUID().toString();
        this.date = Instant.now();
        this.status = PaymentStatus.PENDING;
    }

    public void setPaymentStrategy(PaymentStrategy paymentStrategy) {
        this.paymentStrategy = paymentStrategy;
    }

    public Boolean processPayment() {
        if (paymentStrategy == null) {
            throw new IllegalStateException("Payment strategy is not set");
        }

        Boolean ok = paymentStrategy.pay(this.amount);
        if (ok) {
            setStatus(PaymentStatus.SUCCESS);
        }
        return ok;
    }

    public PaymentStatus getStatus() {
        return status;
    }

    public void setStatus(PaymentStatus status) {
        this.status = status;
    }
}
