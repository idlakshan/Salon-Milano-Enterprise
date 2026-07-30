package com.milano.entity;

import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;

import java.time.LocalDateTime;
import java.util.UUID;

@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
@Table
public class Notification {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;
    private String type;
    private String description;
    @Column(name = "is_read", nullable = false)
    private boolean read=false;
    private UUID userId;
    private UUID bookingId;
    private UUID salonId;
    @CreationTimestamp
    private LocalDateTime createdAt;

}
