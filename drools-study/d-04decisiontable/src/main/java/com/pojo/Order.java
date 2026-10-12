package com.pojo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.ToString;

@Data
public class Order {
    private String id;
    private double amount;
    private boolean blocked;
    private double discount;

    public Order(String id, double amount) {
        this.id = id;
        this.amount = amount;
    }

    public Order(String id, double amount, boolean blocked) {
        this.id = id;
        this.amount = amount;
        this.blocked = blocked;
    }
}
