package com.example.demo.dto.item;

import com.example.demo.dto.imageitem.ImageItemDto;
import com.example.demo.dto.itemdetail.ItemDetailDto;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.List;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class ItemDto {
    private Integer id;
    private String name;
    private Integer igId;
    private Double buyPrice;
    private Double sellPrice;
    private Integer discount;
    private Integer  rating;
    private String description;
    private LocalDateTime lastUpdateAt;
    private List<ImageItemDto> imagesItem;
    private List<ItemDetailDto> itemDetails;
}
