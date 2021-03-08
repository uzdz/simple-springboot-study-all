package com.uzdz.study.sharding.mapper;

import com.uzdz.study.sharding.entity.OrderConfig;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface OrderConfigDao {
    List<OrderConfig> getOrderConfig();
}

