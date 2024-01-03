package com.example.demo.dto.orderdeliverystatusdetail;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class OrderDeliveryStatusDetailDto {
    private Integer id;
    private Integer deliveryStatusId;
    private Integer orderId;
    private LocalDateTime lastUpdateAt;
    private String desc;
}
