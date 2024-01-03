package com.example.demo.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.List;

@Table(name = "delivery_status")
@Entity
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Data
public class DeliveryStatus implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    @Column(name = "DESCRIPTION")
    private String description;
    @JsonIgnore
    @OneToMany(mappedBy = "deliveryStatus",fetch = FetchType.LAZY)
    private List<OrderDeliveryStatusDetail> orderDeliveryStatusDetails;
}
