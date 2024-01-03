package com.example.demo.dto.imageitem;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ImageSaveDto {
    private String image;
    private Integer itemId;
}
