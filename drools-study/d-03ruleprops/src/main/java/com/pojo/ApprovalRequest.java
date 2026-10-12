package com.pojo;

import lombok.AllArgsConstructor;
import lombok.Data;

@AllArgsConstructor
@Data
public class ApprovalRequest {
    private Order order;   // 关联的订单
}