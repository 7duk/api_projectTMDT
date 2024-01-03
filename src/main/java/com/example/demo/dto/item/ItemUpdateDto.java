package com.example.demo.dto.item;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class ItemUpdateDto {
    private String name;
    private String description;
    private Integer discount;
    private String lastUpdateAt;
}
