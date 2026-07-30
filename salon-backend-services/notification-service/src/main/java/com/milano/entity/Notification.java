package com.milano.entity;

import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;

import java.time.LocalDateTime;
import java.util.UUID;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Table(name = "notifications")
public class Notification {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    private String type;

    private String description;

    @Column(name = "is_read")
    private boolean read = false;

    private UUID userId;

    private UUID bookingId;

    private UUID salonId;

    @CreationTimestamp
    private LocalDateTime createdAt;
}