package com.example.demo.dto.itemdetail;

import com.example.demo.dto.color.ColorDto;
import com.example.demo.dto.item.ItemDto;
import com.example.demo.dto.orderdetail.OrderDetailDto;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ItemDetailDto {
    private Integer id;
    private Integer itemId;
    private Integer amount;
    private ColorDto color;
    private Integer colorId;
    private ItemDto itemDto;
}
