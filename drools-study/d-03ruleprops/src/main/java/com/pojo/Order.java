package com.pojo;

import lombok.AllArgsConstructor;
import lombok.Data;

@AllArgsConstructor
@Data
public class Order {
    private String id;
    private double amount;
    private boolean blocked;   // 是否被风控拦截
}

