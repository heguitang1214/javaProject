package com.tang.proxy;

public class PaymentImpl implements Payment {
    @Override
    public void rent(double fee) {
        System.out.println("支付租金: " + fee);
    }
}
