package com.example.demo.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@Table(name = "image_item")
@Entity
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Data
public class ImageItem implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    @Column(name = "IMAGE")
    private String image;
    @Column(name ="ITEM_ID")
    private Integer itemId;
    @JsonIgnore
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "ITEM_ID",insertable = false,updatable = false)
    private Item item;
}
