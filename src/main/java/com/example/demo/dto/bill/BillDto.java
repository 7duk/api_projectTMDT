package com.example.demo.dto.bill;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Builder
@NoArgsConstructor
@AllArgsConstructor
@Data
public class BillDto {
    private Integer id;
    private LocalDateTime createAt;
    private Integer orderId;
    private Double fee;
}
