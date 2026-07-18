package com.milano.entity;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalTime;
import java.util.List;
import java.util.UUID;

@Entity
@Table(name = "salons")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@ToString
public class Salon {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    @Column(nullable = false, length = 100)
    private String name;

    @ElementCollection
    private List<String> images;

    @Column(nullable = false, length = 255)
    private String address;

    @Column(nullable = false, length = 15)
    private String phoneNumber;

    @Column(nullable = false, length = 255)
    private String email;

    @Column(nullable = false, length = 50)
    private String city;

    @Column(name = "is_open", nullable = false)
    private boolean isOpen;

    @Column(name = "home_service", nullable = false)
    private boolean homeService;

    @Column(nullable = false)
    private boolean active = true;

    @Column(name = "owner_id", nullable = false)
    private UUID ownerId;

    @Column(name = "open_time", nullable = false)
    private LocalTime openTime;

    @Column(name = "close_time", nullable = false)
    private LocalTime closeTime;
}
