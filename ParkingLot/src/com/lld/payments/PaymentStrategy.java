package com.lld.payments;

public interface PaymentStrategy {
    Boolean pay(double amount);
}
