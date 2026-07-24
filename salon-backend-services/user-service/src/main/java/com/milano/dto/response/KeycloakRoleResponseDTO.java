package com.milano.dto.response;

import lombok.*;

import java.util.Map;
import java.util.Objects;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@ToString
@Builder
public class KeycloakRoleResponseDTO {
    private String id;
    private String name;
    private String description;
    private boolean composite;
    private boolean clientRole;
    private String containerId;
    private Map<String, Object> attributes;


}
