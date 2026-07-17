package com.milano.entity;

import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.time.LocalDateTime;
import java.util.UUID;

@Entity
@Table(name = "user")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@ToString
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;


    @Column(name = "full_name", nullable = false, length = 150)
    private String fullName;


    @Column(name = "user_name", nullable = false, unique = true, length = 100)
    private String userName;


    @Column(name = "email", nullable = false, unique = true, length = 100)
    private String email;


    @Column(name = "phone_number", length = 20)
    private String phone;


    @Enumerated(EnumType.STRING)
    @Column(name = "role", nullable = false)
    private ROLE_TYPES role;


    @CreationTimestamp
    @Column(name = "created_at", nullable = false, updatable = false)
    private LocalDateTime createdAt;


    @UpdateTimestamp
    @Column(name = "updated_at", nullable = false)
    private LocalDateTime updatedAt;
}