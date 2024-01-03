package com.example.demo.dto.order;

import com.example.demo.dto.itemdetail.ItemDetailDto;
import com.example.demo.dto.orderdetail.OrderDetailDto;
import com.example.demo.dto.orderdetail.OrderDetailSaveDto;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class OrderSaveDto {
    private String deliveryAddress;
    private Double totalFee;
    private LocalDate deliveryDate;
    private LocalDateTime createAt;
    private Integer customerId;
    private String phone;
    private String name;
    private Integer paymentMethodId;
    private String maGiaoDich;
    private List<OrderDetailSaveDto> orderDetailDtos;
}
