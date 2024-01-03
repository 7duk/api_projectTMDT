package com.example.demo.controller;

import com.example.demo.service.DeliveryStatusService;
import com.example.demo.utils.DataTypeUtils;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/deliverystatus")
@RequiredArgsConstructor
public class DeliveryStatusController {
    private final DeliveryStatusService service;
    @GetMapping("/{id}")
    public ResponseEntity<?> getDelivetyStausById(@PathVariable("id") String DeStatusId){
        return service.getDelivetyStausById(DataTypeUtils.ConvertStringToInt(DeStatusId));
    }
}
