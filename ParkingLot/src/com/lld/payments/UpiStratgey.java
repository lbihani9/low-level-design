package com.lld.payments;

public class UpiStratgey implements PaymentStrategy {
    private String upiId;

    public UpiStratgey(String upiId) {
        this.upiId = upiId;
    }

    @Override
    public Boolean pay(double amount) {
        // Add UPI payment logic here.
        return true;
    }
}
