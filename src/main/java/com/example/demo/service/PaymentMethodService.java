package com.example.demo.service;

import com.example.demo.dto.paymentmethod.PaymentMethodDto;
import com.example.demo.entity.PaymentMethod;
import com.example.demo.repository.PaymentMethodRepository;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Transactional
public class PaymentMethodService {
    private final PaymentMethodRepository repository;
    private final ModelMapper mapper;
    public ResponseEntity<List<PaymentMethodDto>> getPayMethodIfNotDeleted(Integer deleteValue) {
        List<PaymentMethod> paymentMethods = repository.getPaymentMethodsByIsDelete(deleteValue);
        if(!paymentMethods.isEmpty()){
            List<PaymentMethodDto> paymentMethodDtos = paymentMethods.stream()
                    .map(paymentMethod -> mapper.map(paymentMethod, PaymentMethodDto.class)).toList();
            return new ResponseEntity<>(paymentMethodDtos, HttpStatus.OK);
        }
        return new ResponseEntity<>(HttpStatus.NOT_FOUND);

    }
}
