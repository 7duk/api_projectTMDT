package com.example.demo.dto.itemgroup;


import com.example.demo.dto.item.ItemDto;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Data
@Getter
@Setter
public class ItemGroupDto {
    private Integer id;
    private String name;
    private String image;
}
