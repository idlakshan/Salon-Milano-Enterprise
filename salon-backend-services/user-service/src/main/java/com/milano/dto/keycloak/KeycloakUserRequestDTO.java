package com.milano.dto.keycloak;

import lombok.*;

import java.util.ArrayList;
import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@ToString
@Builder

public class KeycloakUserRequestDTO {
    private String username;
    private boolean enabled;
    private boolean emailVerified;
    private String firstName;
    private String lastName;
    private String email;
    @Builder.Default
    private List<CredentialDTO> credentials = new ArrayList<>();

}
