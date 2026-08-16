package com.milano.dto.keycloak;

import lombok.*;

@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class KeycloakUserInfoDTO {
    private String sub;
    private String email;
    private String preferred_username;
}
