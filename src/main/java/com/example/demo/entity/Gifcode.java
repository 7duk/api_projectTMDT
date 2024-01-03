package com.example.demo.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Table(name = "gifcode")
@Entity
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Data
public class Gifcode {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    @Column(name = "CODE")
    private String code;
    @Column(name = "REMAINING_USAGE")
    private Integer remainingUsage;
    @Column(name = "EXPIRATION_DATE")
    private LocalDateTime expirationDate;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "EVENT_ID",insertable = false,updatable = false)
    private Event event;
    @Column(name = "EVENT_ID")
    private Integer eventId;
    @Column(name = "DISCOUNT_CODE")
    private double discountCode;
}
