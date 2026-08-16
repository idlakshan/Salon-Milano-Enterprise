package com.milano.service;

import com.milano.dto.keycloak.KeycloakUserInfoDTO;
import com.milano.dto.keycloak.KeycloakUserResponseDTO;
import com.milano.dto.keycloak.KeycloakUserRoleResponseDTO;
import com.milano.dto.keycloak.TokenResponseDTO;
import com.milano.dto.request.SignupRequestDTO;

import java.util.List;

public interface KeycloakService {

    void createUser(SignupRequestDTO signupRequestDTO);

    TokenResponseDTO getAdminAccessToken(String username, String password, String grantType, String refreshToken);

    KeycloakUserResponseDTO fetchUserByEmail(String email, String token);

    KeycloakUserRoleResponseDTO getRoleByName(String clientUuid, String token, String role);

    void assignRoleToUser(String userId, String clientUuid, List<KeycloakUserRoleResponseDTO> roles, String token);

    KeycloakUserInfoDTO fetchUserProfileByJwt(String token);
}