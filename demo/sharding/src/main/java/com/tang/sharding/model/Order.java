package com.tang.sharding.model;

import java.io.Serializable;

public class Order implements Serializable {
    private static final long serialVersionUID = 1L;

    private int id;
    private int userId;
    private int orderId;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public int getOrderId() {
        return orderId;
    }

    public void setOrderId(int orderId) {
        this.orderId = orderId;
    }
}
