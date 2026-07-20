package com.milano.entity;

import jakarta.persistence.*;
import lombok.*;

import java.util.UUID;

@Entity
@Table(name = "payment_order")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@ToString
public class PaymentOrder {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    @Column(nullable = false)
    private double amount;

    @Column(nullable = false)
    @Enumerated(EnumType.STRING)
    private PAYMENT_STATUS status =PAYMENT_STATUS.PENDING;

    @Column(nullable = false)
    @Enumerated(EnumType.STRING)
    private PAYMENT_METHOD paymentMethod;

    @Column(nullable = true)
    private String paymentLinkId;

    @Column(nullable = false)
    private UUID userId;

    @Column(nullable = false)
    private UUID bookingId;

    @Column(nullable = false)
    private UUID salonId;

}
