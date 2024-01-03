package com.example.demo.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.List;

@Table(name = "item")
@Entity
@Builder
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Item implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    @Column(name = "NAME")
    private String name;
    @Column(name = "ITEM_GROUP_ID")
    private Integer igId;
    @JsonIgnore
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "ITEM_GROUP_ID",insertable = false,updatable = false)
    private ItemGroup itemGroup;
    @Column(name = "BUY_PRICE")
    private Double buyPrice;
    @Column(name = "SELL_PRICE")
    private Double sellPrice;
    @Column(name = "DISCOUNT")
    private Integer discount;
    @Column (name = "RATING")
    private Integer rating;
    @Column(name = "DESCRIPTION")
    private String description;
    @Column(name = "IS_DELETE")
    private Integer isDelete;
    @Column(name ="LAST_UPDATE_AT" )
    private LocalDateTime lastUpdateAt;
    @OneToMany(mappedBy = "item",fetch = FetchType.LAZY)
    private List<ImageItem> imagesItem;
    @OneToMany(mappedBy = "items",fetch = FetchType.LAZY)
    private List<ItemDetail> itemDetails;
}
