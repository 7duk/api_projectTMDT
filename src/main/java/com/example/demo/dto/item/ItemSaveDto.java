package com.example.demo.dto.item;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class ItemSaveDto {
    private String name;
    private Double buyPrice;
    private Double sellPrice;
    private Integer igId;
    private String description;
    private String lastUpdateAt;
}
