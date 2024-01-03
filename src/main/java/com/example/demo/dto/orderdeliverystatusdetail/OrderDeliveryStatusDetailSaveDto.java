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
public class OrderDeliveryStatusDetailSaveDto {
    private Integer deliveryStatusId;
    private String lastUpdateAt;
    private String desc;
}
