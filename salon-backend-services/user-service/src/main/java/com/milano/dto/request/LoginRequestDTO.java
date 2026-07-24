package com.milano.dto.request;

import jakarta.validation.constraints.NotBlank;
import lombok.*;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@ToString
@Builder
public class LoginRequestDTO {

    @NotBlank(message = "Username/Email is mandatory")
    private String email;

    @NotBlank(message = "Password is mandatory")
    private String password;
}