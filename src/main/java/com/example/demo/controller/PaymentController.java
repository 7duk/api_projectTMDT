package com.example.demo.controller;


import com.example.demo.config.PaypalPaymentIntent;
import com.example.demo.config.PaypalPaymentMethod;
import com.example.demo.dto.order.OrderSaveDto;
import com.example.demo.dto.response.Response;
import com.example.demo.service.OrderService;
import com.example.demo.service.PaypalService;
import com.example.demo.service.VnPayService;
import com.example.demo.utils.Currency;
import com.example.demo.utils.UtilsPaypal;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import com.paypal.api.payments.Links;
import com.paypal.api.payments.Payment;
import com.paypal.base.rest.PayPalRESTException;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.servlet.view.RedirectView;

import java.io.Console;
import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;


@RestController
@RequiredArgsConstructor
@RequestMapping("/api/paypal")
public class PaymentController {
//    public static final String URL_PAYPAL_SUCCESS = "/success";
//    public static final String URL_PAYPAL_CANCEL = "/cancel";

    private Logger log = LoggerFactory.getLogger(getClass());
    @Autowired
    private final Currency currency;

    @Autowired
    private final PaypalService paypalService;
    @Autowired
    private  final OrderService orderService;
    private OrderSaveDto temp;


    @Autowired
    private SimpMessagingTemplate simpMessagingTemplate;


    @PostMapping("/payment")
    public ResponseEntity<?> pay(HttpServletRequest request, @RequestBody OrderSaveDto orderSaveDto ){
        String cancelUrl = UtilsPaypal.getBaseURL(request) + "/" + "/cancel";
        String successUrl = UtilsPaypal.getBaseURL(request) +  "/" + "/success";
        try {
            Double fee = currency.convertVndToUsd(orderSaveDto.getTotalFee());
            Payment payment = paypalService.createPayment(
                    fee,
                    "USD",
                    PaypalPaymentMethod.paypal,
                    PaypalPaymentIntent.sale,
                    "payment description",
                    cancelUrl,
                    successUrl);
            for (Links links : payment.getLinks()) {
                System.out.println("links: " + links);
                if (links.getRel().equals("approval_url")) {
                    System.out.println(links.getHref());
                    this.temp = orderSaveDto;
                    return new ResponseEntity<>(links.getHref(),HttpStatus.OK);
                }
            }
        }
		 catch (PayPalRESTException e) {
             log.error(e.getMessage());
             return new ResponseEntity<>(e.getMessage(),HttpStatus.INTERNAL_SERVER_ERROR);
         }
        return null;
    }


    @GetMapping("/cancel")
    public void cancelPay(HttpServletRequest request){
        simpMessagingTemplate.convertAndSend("/topic/messages/" + temp.getCustomerId(), Response.builder().message("PAYPAL ERROR").build());
//        return new RedirectView(  UtilsPaypal.getBaseURL(request) +  "/payment/result" );
    }



    @GetMapping("/success")
    public void successPay(@RequestParam("paymentId") String paymentId, @RequestParam("PayerID") String payerId, HttpServletRequest request) {
        try {
            Payment payment = paypalService.executePayment(paymentId, payerId);
            System.out.println(payment.getState());
            System.out.println(payment.getId());
            System.out.println(payment.getUpdateTime());
            Instant instant = Instant.parse(payment.getUpdateTime());
            ZoneId zoneId = ZoneId.of("Asia/Ho_Chi_Minh");
            ZonedDateTime zonedDateTime = instant.atZone(zoneId);
            LocalDateTime daytime = zonedDateTime.toLocalDateTime();
            System.out.println("daytime:"+daytime);
            String newdaytime = daytime.format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"));
            System.out.println("newdaytime:"+newdaytime);
            LocalDateTime newTime = LocalDateTime.parse(newdaytime, DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"));
            System.out.println("newTime:"+newTime);
            this.temp.setCreateAt(newTime);
            this.temp.setMaGiaoDich(payment.getId());
            if (payment.getState().equals("approved")) {
                try {
                    orderService.saveOrder(temp,1);
                    simpMessagingTemplate.convertAndSend("/topic/messages/" + temp.getCustomerId(), Response.builder().message("PAYPAL SUCCESS").build());
                    System.out.println("vào đây chưa");
                }catch (Exception e){
                    System.out.println(e);
                }
            }
            else{
                simpMessagingTemplate.convertAndSend("/topic/messages/" + temp.getCustomerId(), Response.builder().message("PAYPAL ERROR").build());
            }
        }
		 catch (PayPalRESTException e) {
             log.error(e.getMessage());
         }
//        return new RedirectView(  UtilsPaypal.getBaseURL(request) +  "/payment/result" );
    }
}
