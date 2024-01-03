package com.example.demo.dto.orderdetail;

import com.example.demo.dto.itemdetail.ItemDetailDto;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class OrderDetailSaveDto {
    private Integer orderId;
    private Integer itemDetailDtoId;
    private Integer amount;
    private String note;
    private Integer itemCartId;
}
