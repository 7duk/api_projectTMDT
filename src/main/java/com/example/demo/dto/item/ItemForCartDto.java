package com.example.demo.dto.item;

import com.example.demo.dto.imageitem.ImageItemDto;
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
public class ItemForCartDto {
    private Integer id;
    private String name;
    private Double sellPrice;
    private Integer discount;
    private Integer  rating;
    private String description;
    private List<ImageItemDto> imagesItem;
}
