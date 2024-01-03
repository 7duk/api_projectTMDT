package com.example.demo.controller;

import com.example.demo.dto.item.ItemSaveDto;
import com.example.demo.dto.message.MessageDto;
import com.example.demo.dto.order.OrderSaveDto;
import com.example.demo.dto.response.Response;
import com.example.demo.service.OrderService;
import com.example.demo.service.VnPayService;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Controller;
import org.springframework.stereotype.Service;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.view.RedirectView;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/vnpay")
public class VnPayController {
    @Autowired

    private final VnPayService vnPayService;
    private OrderSaveDto temp;
    private String vnPayRedirectUrl ;
    @Autowired
    private  final OrderService orderService;


    private final String orderInfo = "CHUYEN TIEN THANH TOAN HANG CHO SHOPIEC";

    @Autowired
    private SimpMessagingTemplate simpMessagingTemplate;

    @PostMapping("/payment")
    public ResponseEntity<?> submidOrder(@RequestBody OrderSaveDto orderSaveDto,HttpServletRequest request){
        String baseUrl =  request.getScheme() + "://" + request.getServerName() + ":" + request.getServerPort();
        System.out.println("baseUrl"+baseUrl);
        try{
            String vnpayUrl = vnPayService.createOrder(orderSaveDto, orderInfo, baseUrl);
            this.temp = orderSaveDto;
            this.vnPayRedirectUrl = vnpayUrl;
            return new ResponseEntity<>(vnpayUrl,HttpStatus.OK);
        }
       catch (Exception e){
           return new ResponseEntity<>(e.getMessage(),HttpStatus.INTERNAL_SERVER_ERROR);
       }
    }

    @GetMapping("/result")
    public void payment(HttpServletRequest request){
        int paymentStatus =vnPayService.orderReturn(request);
        String transactionId = request.getParameter("vnp_TransactionNo");
        System.out.println("transactionId:"+transactionId);
        String paymentTime = request.getParameter("vnp_PayDate");
        LocalDateTime daytime = LocalDateTime.parse(paymentTime, DateTimeFormatter.ofPattern("yyyyMMddHHmmss"));
        String newdaytime = daytime.format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"));
        LocalDateTime newTime = LocalDateTime.parse(newdaytime, DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"));

        this.temp.setCreateAt(newTime);
        this.temp.setMaGiaoDich(transactionId);
        if( paymentStatus == 1 ){
            try {
                orderService.saveOrder(temp,1);
                System.out.println("temp.getCustomerId():"+temp.getCustomerId());
                simpMessagingTemplate.convertAndSend("/topic/messages/" + temp.getCustomerId(), Response.builder().message("VNPAY SUCCESS").build());
            }catch (Exception e){
                System.out.println(e.getMessage());
            }
        }else {
            simpMessagingTemplate.convertAndSend("/topic/messages/" + temp.getCustomerId(), Response.builder().message("VNPAY ERROR").build());
        }
//        return new RedirectView(this.vnPayRedirectUrl);
    }
}
