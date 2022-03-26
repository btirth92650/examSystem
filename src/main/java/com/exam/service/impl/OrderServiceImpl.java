package com.exam.service.impl;

import com.exam.service.OrderService;
import com.exam.repo.MyOrderRepository;
import com.exam.model.exam.MyOrder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class OrderServiceImpl implements OrderService {

    @Autowired
    MyOrderRepository orderRepository;

    @Override
    public MyOrder saveOrder(MyOrder myOrder) {
        return orderRepository.save(myOrder);
    }

    @Override
    public MyOrder getOrderIdBy(String orderId) {
        return orderRepository.findByOrderId(orderId);
    }
}