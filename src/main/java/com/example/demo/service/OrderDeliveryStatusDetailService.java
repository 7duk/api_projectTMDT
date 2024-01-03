package com.example.demo.service;

import com.example.demo.dto.orderdeliverystatusdetail.OrderDeliveryStatusDetailDto;
import com.example.demo.dto.orderdeliverystatusdetail.OrderDeliveryStatusDetailSaveDto;
import com.example.demo.dto.response.Response;
import com.example.demo.repository.OrderDeliveryStatusDetailRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

@Service
@Transactional
@RequiredArgsConstructor
public class OrderDeliveryStatusDetailService {
    private final OrderDeliveryStatusDetailRepository repository;
    public ResponseEntity<?> updateDsByOrderId(Integer orderId, OrderDeliveryStatusDetailSaveDto dto) {
        try{
            LocalDateTime lastUpdateAt = LocalDateTime.parse(dto.getLastUpdateAt(),DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss")) ;
            repository.updateDeliveryStatusByOrderId(dto.getDeliveryStatusId(), lastUpdateAt,dto.getDesc(),orderId);
        }
        catch (Exception e){
            return new ResponseEntity<>(Response.builder().message(e.getMessage()).build(),HttpStatus.INTERNAL_SERVER_ERROR);
        }
        return new ResponseEntity<>(Response.builder().message("SUCCESS").build(), HttpStatus.OK);
    }
}
