package com.pojo;

import lombok.Data;

@Data
public class Order2 {
    private String id;
    private double amount;
    private boolean blocked;
    private double discount;

    public Order2(String id, double amount) {
        this.id = id;
        this.amount = amount;
    }

    public Order2(String id, double amount, boolean blocked) {
        this.id = id;
        this.amount = amount;
        this.blocked = blocked;
    }
}
