package com.example.demo.dto.itemdetail;

import com.example.demo.dto.color.ColorDto;
import com.example.demo.dto.item.ItemForCartDto;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ItemDetailForCartDto {
    private  Integer id;
    private ItemForCartDto item;
    private ColorDto color;
}
