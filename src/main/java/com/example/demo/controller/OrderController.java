package com.example.demo.controller;

import com.example.demo.dto.order.DateRangeRequest;
import com.example.demo.dto.order.OrderSaveDto;
import com.example.demo.dto.orderdeliverystatusdetail.OrderDeliveryStatusDetailDto;
import com.example.demo.dto.orderdeliverystatusdetail.OrderDeliveryStatusDetailSaveDto;
import com.example.demo.dto.response.Response;
import com.example.demo.service.OrderDeliveryStatusDetailService;
import com.example.demo.service.OrderService;
import com.example.demo.utils.DataTypeUtils;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/orders")
public class OrderController {
    private final OrderService service;
    private final OrderDeliveryStatusDetailService ODSDService;
    @Autowired
    private SimpMessagingTemplate simpMessagingTemplate;

    @GetMapping("")
    public ResponseEntity<?> getAllOrder(@RequestParam(name="page",required = false,defaultValue = "1") Integer page,
                                         @RequestParam(name = "size", required = false, defaultValue = "10")  Integer size,
                                         @RequestParam(name = "sort", required = false, defaultValue = "ASC")  String sort
    ){
        return service.getAllOrder(page,size,sort);
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> getOrderById(@PathVariable("id") String OrderId){
        return service.getOrderById(DataTypeUtils.ConvertStringToInt(OrderId));
    }

    @GetMapping("/user/{customer-id}")
    public ResponseEntity<?> getOrderByCustomerId(@PathVariable("customer-id") String CustomerId,
                                                  @RequestParam(name="page",required = false,defaultValue = "1") Integer page,
                                                  @RequestParam(name = "size", required = false, defaultValue = "10")  Integer size,
                                                  @RequestParam(name = "sort", required = false, defaultValue = "ASC")  String sort){
        return service.getOrderByCustomerId(DataTypeUtils.ConvertStringToInt(CustomerId),page,size,sort);
    }

    @PostMapping("/user/statistical")
    public ResponseEntity<?> getOrderByDateRange(@RequestBody @Validated DateRangeRequest dateRangeRequest){
        return service.getOrderByDateRange(dateRangeRequest);
    }

    @PostMapping("/paylive")
    public ResponseEntity<?> saveOrder(@RequestBody OrderSaveDto orderSaveDto){
        try{
            service.saveOrder(orderSaveDto,0);
            System.out.println("orderSaveDto.getCustomerId()"+orderSaveDto.getCustomerId());
            simpMessagingTemplate.convertAndSend("/topic/messages/" + orderSaveDto.getCustomerId(), Response.builder().message("PAYLIVE SUCCESS").build());
            return new ResponseEntity<>( Response.builder().message("PAYLIVE SUCCESS").build(), HttpStatus.OK);
        }
        catch (Exception e){
            simpMessagingTemplate.convertAndSend("/topic/messages/" + orderSaveDto.getCustomerId(), Response.builder().message("PAYLIVE ERROR").build());
            return new ResponseEntity<>( Response.builder().message("PAYLIVE ERROR").build(), HttpStatus.INTERNAL_SERVER_ERROR);
        }

    }
    @PatchMapping("/order/{orderId}")
    public ResponseEntity<?> updateDeveliveryStatusByOrderId(@PathVariable("orderId") String orderId, @RequestBody OrderDeliveryStatusDetailSaveDto oDSDDto){
        return ODSDService.updateDsByOrderId(DataTypeUtils.ConvertStringToInt(orderId),oDSDDto);
    }
    @PatchMapping("/paylive/{id}")
    public ResponseEntity<?> updateStatusPayment(@PathVariable("id") String id){
        return service.updateStatusPayment(DataTypeUtils.ConvertStringToInt(id));
    }
}
