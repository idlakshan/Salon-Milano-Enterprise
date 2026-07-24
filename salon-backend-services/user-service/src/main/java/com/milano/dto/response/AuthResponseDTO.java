package com.milano.dto.response;

import com.milano.entity.ROLE_TYPES;
import lombok.*;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@ToString
@Builder
public class AuthResponseDTO {
    private String jwt;
    private String refresh_token;
    private String message;
    private String title;
    private ROLE_TYPES role;
}
