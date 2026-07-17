package com.milano.dto.request;

import com.milano.entity.ROLE_TYPES;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import lombok.*;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@ToString
@Builder
public class UserRequestDTO {

    @NotBlank(message = "Full name is mandatory")
    private String fullName;

    @NotBlank(message = "Username is mandatory")
    private String username;

    @NotBlank(message = "Email is mandatory")
    @Email(message = "Email should be valid")
    private String email;

    private String phone;

    private ROLE_TYPES role;
}