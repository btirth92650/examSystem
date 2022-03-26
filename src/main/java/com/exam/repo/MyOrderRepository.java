package com.exam.repo;

import com.exam.model.exam.MyOrder;
import org.springframework.data.jpa.repository.JpaRepository;

public interface MyOrderRepository extends JpaRepository<MyOrder, Long> {

    //getting orderId
    public MyOrder  findByOrderId(String orderId);

}
