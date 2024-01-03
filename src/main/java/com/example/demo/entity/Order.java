package com.example.demo.entity;


import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

@Table(name = "`order`")
@Entity
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class Order {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    @Column(name = "DELIVERY_ADDRESS")
    private String deliveryAddress;
    @Column(name = "TOTAL_FEE")
    private Double totalFee;
    @Column(name = "DELIVERY_DATE")
    private LocalDate deliveryDate;
    @Column(name = "CREATE_AT")
    private LocalDateTime createAt;
    @Column(name = "CUSTOMER_ID")
    private Integer customerId;
    @Column(name = "PAYMENT_METHOD_ID")
    private Integer payMethodId;
    @Column(name = "MAGIAODICH")
    private String maGiaoDich;
    @Column(name = "STATUS_PAYMENT")
    private Integer statusPayment;
    @Column(name = "PHONE")
    private String phone;
    @Column(name = "NAME")
    private String name;
    @JsonIgnore
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "CUSTOMER_ID",insertable = false,updatable = false)
    private User users;
    @JsonIgnore
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "PAYMENT_METHOD_ID",insertable = false,updatable = false)
    private PaymentMethod paymentMethod;
    @OneToOne(mappedBy = "order", fetch = FetchType.LAZY)
    private Bill bill;
    @OneToMany(mappedBy = "order", fetch = FetchType.LAZY)
    private List<OrderDetail> orderDetails;
    @OneToOne(mappedBy = "order", fetch = FetchType.LAZY)
    private OrderDeliveryStatusDetail orderDeliveryStatusDetails;

}
