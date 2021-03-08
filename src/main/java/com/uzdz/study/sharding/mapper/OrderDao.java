package com.uzdz.study.sharding.mapper;

import com.uzdz.study.sharding.entity.Order;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface OrderDao {

    // 查询某个用户订单列表
    List<Order> getOrders(Order order);

    // 插入订单信息
    int addOrder(Order orderInfo);
}

