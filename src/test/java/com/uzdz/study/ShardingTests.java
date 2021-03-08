package com.uzdz.study;

import com.uzdz.study.sharding.entity.Order;
import com.uzdz.study.sharding.mapper.OrderDao;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.List;

@SpringBootTest(classes = SimpleStudyApplication.class)
@RunWith(SpringRunner.class)
public class ShardingTests {

    @Autowired
    OrderDao orderDao;

    @Test
    public void insert() {
        for (int i = 0; i < 2; i++) {
            long userId = i;
            long orderId = i + 1;
            Order order = new Order();
            order.setUserName("阿离");
            order.setUserId(userId);
            order.setOrderId(orderId);
            int result = orderDao.addOrder(order);
        }
    }

    @Test
    public void insertAndSelect() {
        long userId = 0;
        long orderId = 1 + 1;
        Order order = new Order();
        order.setUserName("阿离");
        order.setUserId(userId);
        order.setOrderId(orderId);
        //int result = orderDao.addOrder(order);

        List<Order> orders = orderDao.getOrders(order);
        orders.stream().forEach(System.out::println);
    }
}
