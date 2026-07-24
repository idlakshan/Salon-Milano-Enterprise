package com.milano.dto;

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