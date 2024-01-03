package com.example.demo.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.List;

@Table(name = "payment_method")
@Entity
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class PaymentMethod implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    @Column(name = "DESC")
    private String desc;
    @Column(name = "IS_DELETE")
    private Integer isDelete;
    @JsonIgnore
    @OneToMany(mappedBy = "paymentMethod",fetch = FetchType.LAZY)
    private List<Order> Orders;
}
