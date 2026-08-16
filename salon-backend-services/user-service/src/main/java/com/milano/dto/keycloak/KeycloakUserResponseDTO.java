package com.milano.dto.keycloak;

import lombok.*;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@ToString
@Builder
public class KeycloakUserResponseDTO {
    private String id;
    private String firstName;
    private String lastName;
    private String email;
    private String username;
}
