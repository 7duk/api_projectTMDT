package com.example.demo.service;

import com.example.demo.dto.orderdetail.OrderDetailDto;
import com.example.demo.dto.response.Response;
import com.example.demo.entity.OrderDetail;
import com.example.demo.repository.OrderDetailRepository;
import lombok.AllArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@AllArgsConstructor
@Transactional
public class OrderDetailSevice {
    private final OrderDetailRepository repository;
    private final ModelMapper mapper;

    public ResponseEntity<List<OrderDetailDto>> getAllOrderDetail() {
        List<OrderDetail> orderDetails = repository.getAllOrderDetail();
        if (orderDetails != null) {
            List<OrderDetailDto> orderDetailDtos = orderDetails.stream().map(od ->{
                OrderDetailDto orderDetailDto = mapper.map(od, OrderDetailDto.class);
//                orderDto.setBill(o.getBill().get);
//                BillDto billDto = mapper.map(o.getBill(), BillDto.class);
//                billDto.setOrderId(o.getBill().getOrderId());
                return orderDetailDto;
            }).toList();
            return new ResponseEntity<>(orderDetailDtos, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    public ResponseEntity<?> getAllOrderDetailByOrderId(Integer orderId){
        List<OrderDetail> orderDetails = repository.getAllOrderDetailByOrderId(orderId);
        if (orderDetails != null) {
            List<OrderDetailDto> orderDetailDtos = orderDetails.stream().map(od ->{
                OrderDetailDto orderDetailDto = mapper.map(od, OrderDetailDto.class);
                return orderDetailDto;
            }).toList();
            return new ResponseEntity<>(orderDetailDtos, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(Response.builder().message("ID IS NOT EXIST").build(),HttpStatus.NOT_FOUND);
        }
    }
}
