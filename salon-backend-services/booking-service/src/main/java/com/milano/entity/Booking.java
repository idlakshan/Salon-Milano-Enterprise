package com.milano.entity;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;
import java.util.Set;
import java.util.UUID;

@Entity
@Table(name = "bookings")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@ToString
public class Booking {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    private UUID salonId;

    private UUID customerId;

    @Column(name = "start_time",nullable = false)
    private LocalDateTime startTime;

    @Column(name = "end_time", nullable = false)
    private LocalDateTime endTime;

    @ElementCollection
    @Column(nullable = false)
    private Set<UUID> serviceIds;

    @Enumerated(EnumType.STRING)
    @Column(name = "booking_status", nullable = false)
    private BOOKING_STATUS status = BOOKING_STATUS.PENDING;

    @Column(name = "total_price", nullable = false)
    private double totalPrice;

}
