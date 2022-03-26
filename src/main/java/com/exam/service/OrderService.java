package com.exam.service;

import com.exam.model.exam.MyOrder;

public interface OrderService {

    //    Save order
    public MyOrder saveOrder(MyOrder myOrder);

    //calling repository method
    public MyOrder getOrderIdBy(String orderId);

}
