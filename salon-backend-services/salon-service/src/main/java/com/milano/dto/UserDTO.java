package com.milano.dto;

import com.milano.entity.ROLE_TYPES;
import lombok.*;

import java.time.LocalDateTime;
import java.util.UUID;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Builder
@ToString
public class UserDTO {

    private UUID id;
    private String fullName;
    private String username;
    private String email;
    private String phone;
    private ROLE_TYPES role;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
}