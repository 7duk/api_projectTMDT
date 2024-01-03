package com.example.demo.dto.itemcart;

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
public class ItemCartSaveDto {
    private Integer userId;
    private Integer itemDetailId;
    private Integer quantity;
}
