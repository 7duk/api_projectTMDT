package com.example.demo.controller;

import com.example.demo.dto.paymentmethod.PaymentMethodDto;
import com.example.demo.service.PaymentMethodService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/paymentmethods")
public class PaymentMethodController {
    private final PaymentMethodService service;
    @GetMapping("")
    public ResponseEntity<List<PaymentMethodDto>> getPaymentMethod() {
        return service.getPayMethodIfNotDeleted(0);
    }

    @GetMapping("/id")
    public ResponseEntity<List<PaymentMethodDto>> getPaymentMethodById() {
        return service.getPayMethodIfNotDeleted(0);
    }
}
