package com.pojo;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
public class Order {
    public final static double NO_DISCOUNT = 1.0;
    private User user;
    private double amount;
    private double discount = NO_DISCOUNT;   // 默认不打折
    private boolean refunded;   // 是否已退款

    public Order(User user, double amount, double discount) {
        this.user = user;
        this.amount = amount;
        this.discount = discount;
    }

    public Order(User user, double amount, double discount, boolean refunded) {
        this.user = user;
        this.amount = amount;
        this.discount = discount;
        this.refunded = refunded;
    }

    public Order(User user, double amount) {
        this.user = user;
        this.amount = amount;
    }
}