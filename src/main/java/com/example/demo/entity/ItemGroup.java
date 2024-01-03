package com.example.demo.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.List;

@Table(name = "item_group")
@Entity
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Data
public class ItemGroup implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    @Column(name = "NAME")
    private String name;
    @Column(name = "IS_DELETE")
    private Integer isDelete;
    @Column(name="IMAGE")
    private String image;
    @OneToMany(mappedBy = "itemGroup",fetch = FetchType.LAZY)
    private List<Item> items;
}
