package com.example.demo.service;


import com.example.demo.dto.bill.BillDto;
import com.example.demo.dto.response.Response;
import com.example.demo.entity.Bill;
import com.example.demo.repository.BillRepository;
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
public class BillService {
    private final BillRepository repository;
    private final ModelMapper mapper;
    public ResponseEntity<?> saveBill(BillDto billDto) {
        Bill bill = mapper.map(billDto, Bill.class);
        bill.setOrderId(billDto.getOrderId());
        System.out.println(bill.toString());
        try {
            repository.saveBill(bill);
            return new ResponseEntity<>(Response.builder().message("SUCCESS").build(), HttpStatus.CREATED);
            //status code 201
        } catch (Exception e) {
            return new ResponseEntity<>(Response.builder().message(e.getMessage()).build(), HttpStatus.INTERNAL_SERVER_ERROR);
            //status code 500
        }

    }
    public ResponseEntity<List<BillDto>> getAllBill(Integer limit,Integer page){
        Integer offSet = (page-1)*limit;
        List<Bill> bills = repository.getAllBill(limit,offSet);
        List<BillDto> billDtos = bills.stream().map(b ->{
            BillDto billDto = mapper.map(b, BillDto.class);
            billDto.setOrderId(b.getOrderId());
            System.out.println(billDto.toString());
            return billDto;
        }).toList();
        return new ResponseEntity<>(billDtos, HttpStatus.OK);
    }
}
