package com.exam.controller;

import com.exam.model.exam.MyOrder;
import com.exam.service.OrderService;
import com.razorpay.Order;
import com.razorpay.RazorpayClient;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequestMapping("api")
@CrossOrigin("*")

public class PaymentOrderController {


    //    Autowaring
    @Autowired
    OrderService orderService;

    @PostMapping(value = "/create-order")
    @ResponseBody
    public ResponseEntity<?> acceptPayment(@RequestBody Map<String, Object> payment) {
        System.out.println(payment);
        int amount = Integer.parseInt(payment.get("amount").toString());

        try {
            RazorpayClient razorpayClient = new RazorpayClient("rzp_test_kcY7ymrYYMfmyA", "pD6LdENjLJuhaqEWtRs3PnJx");
            JSONObject options = new JSONObject();
            options.put("amount", amount * 100);
            options.put("currency", "INR");
            options.put("receipt", "txn_123456");

//            Now calling Razorpay Server
            Order order = razorpayClient.Orders.create(options);

            //save your database
            MyOrder myOrder = new MyOrder();

//            set data
            myOrder.setAmount(order.get("amount") + "");
            myOrder.setOrderId(order.get("id"));
            myOrder.setReceipt(order.get("receipt"));
            myOrder.setPaymentId("null");
            myOrder.setStatus("created");

            MyOrder myOrder1 = orderService.saveOrder(myOrder);


            System.out.println(myOrder1.toString());
            System.out.println("======================> " + order);
            return ResponseEntity.ok(order.toString());

        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.ok(HttpStatus.BAD_REQUEST);
        }
    }


//    @PostMapping(value = "/update-order")
//    public ResponseEntity<?> updateOrderFromServer(@RequestBody Map<String , Object> data){
//        System.out.println("======Data==========>" + data.toString());
//        MyOrder myOrder = this.orderService.getOrderIdBy(data.get("order_id").toString());
//        System.out.println("=============MyOrder======================> " + myOrder);
//        myOrder.setPaymentId(data.get("payment_id").toString());
//        myOrder.setStatus(data.get("status").toString());
//        return ResponseEntity.ok("");
//    }
}

