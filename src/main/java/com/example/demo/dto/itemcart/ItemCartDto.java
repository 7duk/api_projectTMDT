package com.example.demo.dto.itemcart;

import com.example.demo.dto.itemdetail.ItemDetailDto;
import com.example.demo.dto.itemdetail.ItemDetailForCartDto;
import com.example.demo.dto.user.UserDto;
import com.example.demo.entity.ItemDetail;
import com.example.demo.entity.User;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class ItemCartDto {
    private Integer id;
    private Integer userId;
    private ItemDetailForCartDto itemDetail;
    private Integer quantity;
}
