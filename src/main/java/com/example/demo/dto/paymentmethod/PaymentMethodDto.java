package com.example.demo.dto.paymentmethod;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;


@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class PaymentMethodDto {
    private Integer id;
    private String desc;
}
