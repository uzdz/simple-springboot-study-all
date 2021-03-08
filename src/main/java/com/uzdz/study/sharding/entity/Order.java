package com.uzdz.study.sharding.entity;

import lombok.Data;
import lombok.ToString;

@Data
@ToString
public class Order {

    private String id;
    private Long userId;
    private Long orderId;
    private String userName;

}