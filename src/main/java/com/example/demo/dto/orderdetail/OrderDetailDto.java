package com.example.demo.dto.orderdetail;

import com.example.demo.dto.itemdetail.ItemDetailDto;
import com.example.demo.entity.ItemDetail;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class OrderDetailDto {
    private Integer id;
    private Integer orderId;
    private ItemDetailDto itemDetailDto;
    private Integer amount;
    private String note;
}
