package com.example.demo.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@Entity
@Table(name = "item_cart")
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Data
public class ItemCart implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    @Column(name = "USER_ID")
    private Integer userId;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "USER_ID",insertable = false,updatable = false)
    private User user;
    @Column(name = "ITEM_DETAIL_ID")
    private Integer itemDetailId;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "ITEM_DETAIL_ID",insertable = false,updatable = false)
    private ItemDetail itemDetail;
    @Column(name = "QUANTITY")
    private Integer quantity;
//    @Column(name = "IS_DELETE")
//    private Integer isDelete;
}
