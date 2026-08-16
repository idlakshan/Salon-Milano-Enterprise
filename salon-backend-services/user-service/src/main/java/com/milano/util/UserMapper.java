package com.milano.util;

import com.milano.dto.keycloak.CredentialDTO;
import com.milano.dto.request.SignupRequestDTO;
import com.milano.dto.keycloak.KeycloakUserRequestDTO;
import com.milano.dto.request.UserRequestDTO;
import com.milano.dto.response.AuthResponseDTO;
import com.milano.dto.keycloak.TokenResponseDTO;
import com.milano.dto.response.UserResponseDTO;
import com.milano.entity.ROLE_TYPES;
import com.milano.entity.User;
import org.springframework.stereotype.Component;

import java.util.Collections;

@Component
public class UserMapper {

    public User toUser(UserRequestDTO userRequestDTO) {
        return User.builder().fullName(userRequestDTO.getFullName())
                .userName(userRequestDTO.getUsername())
                .email(userRequestDTO.getEmail())
                .phone(userRequestDTO.getPhone()).
                role(userRequestDTO.getRole())
                .build();
    }

    public UserResponseDTO toUserResponseDTO(User user) {
        return UserResponseDTO.builder().id(user.getId())
                .fullName(user.getFullName())
                .username(user.getUserName())
                .email(user.getEmail())
                .phone(user.getPhone())
                .role(user.getRole())
                .createdAt(user.getCreatedAt())
                .updatedAt(user.getUpdatedAt())
                .build();
    }

    // 1. SignupRequestDTO -> User Entity
    public User toUserEntity(SignupRequestDTO req) {
        return User.builder()
                .fullName(req.getFullName())
                .userName(req.getUsername())
                .email(req.getEmail())
                .phone(req.getPhone())
                .role(req.getRole())
                .build();
    }

    // SignupRequestDTO -> Keycloak UserRegistrationRequestDTO
    public KeycloakUserRequestDTO toKeycloakUserRequest(SignupRequestDTO req) {
        CredentialDTO credentialDTO = CredentialDTO.builder()
                .type("password")
                .value(req.getPassword())
                .temporary(false)
                .build();

        return KeycloakUserRequestDTO.builder()
                .username(req.getUsername())
                .email(req.getEmail())
                .firstName(req.getFirstName())
                .lastName(req.getLastName())
                .enabled(true)
                .emailVerified(true)
                .credentials(Collections.singletonList(credentialDTO))
                .build();
    }

    // Auth Response Builder
    public AuthResponseDTO toAuthResponse(String title, String message, TokenResponseDTO tokenResponse, Object role) {
        AuthResponseDTO response = new AuthResponseDTO();
        response.setTitle(title);
        response.setMessage(message);
        response.setJwt(tokenResponse.getAccessToken());
        response.setRefresh_token(tokenResponse.getRefreshToken());
        if (role != null) {
            response.setRole((ROLE_TYPES) role);
        }
        return response;
    }

}
