package com.example.demo.service;

import com.example.demo.dto.deliverystatus.DeliveryStatusDto;
import com.example.demo.entity.DeliveryStatus;
import com.example.demo.repository.DeliveryStatusRepository;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional
@RequiredArgsConstructor
public class DeliveryStatusService {
    private final DeliveryStatusRepository repository;
    private final ModelMapper mapper;

    public ResponseEntity<DeliveryStatusDto> getDelivetyStausById(Integer DeStatusId){
        DeliveryStatus deliveryStatuses = repository.getDelivetyStausById(DeStatusId);
        if (deliveryStatuses != null) {
            DeliveryStatusDto deliveryStatusDto =  mapper.map(deliveryStatuses, DeliveryStatusDto.class);
            return new ResponseEntity<>(deliveryStatusDto, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

//    public ResponseEntity<DeliveryStatusDto> getOrderDeliveryByIdDelivery(Integer CustomerId, Integer DeliveryId){
//        DeliveryStatus deliveryStatuses = repository.getOrderDeliveryByIdDelivery(CustomerId, DeliveryId);
//        if (deliveryStatuses != null) {
//            DeliveryStatusDto deliveryStatusDto =  mapper.map(deliveryStatuses, DeliveryStatusDto.class);
//            return new ResponseEntity<>(deliveryStatusDto, HttpStatus.OK);
//        } else {
//            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
//        }
//    }

}

