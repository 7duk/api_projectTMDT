package com.example.demo.controller;

import com.example.demo.service.OrderDetailSevice;
import com.example.demo.utils.DataTypeUtils;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/orderdetails")
public class OrderDetailController {
    private final OrderDetailSevice service;

    @GetMapping("")
    public ResponseEntity<?> getAllOrderDetail(){
        return service.getAllOrderDetail();
    }

    @GetMapping("/{order-id}")
    public ResponseEntity<?> getAllOrderDetailByOrderId(@PathVariable("order-id")  String OrderId){
        return service.getAllOrderDetailByOrderId(DataTypeUtils.ConvertStringToInt(OrderId));
    }

}
