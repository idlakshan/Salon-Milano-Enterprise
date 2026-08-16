package com.milano.dto.keycloak;

import lombok.*;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@ToString
@Builder
public class CredentialDTO {
    private String type;
    private String value;
    private boolean temporary;
}