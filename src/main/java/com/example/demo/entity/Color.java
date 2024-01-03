package com.example.demo.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

@Table(name = "color")
@Entity
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class Color implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    @Column(name = "DESCRIPTION")
    private String code;
    @JsonIgnore
    @OneToMany(mappedBy = "color",fetch = FetchType.LAZY)
    private List<ItemDetail> itemDetails;
}
