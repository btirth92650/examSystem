package com.exam.model.exam;

import lombok.Data;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@Data
@Entity
public class MyOrder {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long myOrderId;
    private String orderId;
    private String amount;
    private String receipt;
    private String status;
    private String paymentId;


}
