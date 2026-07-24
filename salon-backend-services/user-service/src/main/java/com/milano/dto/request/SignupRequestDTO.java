package com.milano.dto.request;

import com.milano.entity.ROLE_TYPES;
import lombok.*;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@ToString
@Builder
public class SignupRequestDTO {
    private String firstName;
    private String lastName;
    private String email;
    private String password;
    private String username;
    private ROLE_TYPES role;
    private String phone;
    private String fullName;


}
