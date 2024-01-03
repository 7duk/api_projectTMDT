package com.example.demo.dto.order;

import com.example.demo.dto.bill.BillOfOrderDto;
import com.example.demo.dto.orderdeliverystatusdetail.OrderDeliveryStatusDetailDto;
import com.example.demo.dto.orderdetail.OrderDetailDto;
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
public class OrderDto {
    private Integer id;
    private String deliveryAddress;
    private Double totalFee;
    private LocalDate deliveryDate;
    private LocalDateTime createAt;
    private Integer customerId;
    private Integer paymentMethodId;
    private Integer paymentStatus;
    private String phone;
    private String name;
    private BillOfOrderDto bill;
    private List<OrderDetailDto> orderDetails;
    private OrderDeliveryStatusDetailDto orderDeliveryStatusDetails;
}
