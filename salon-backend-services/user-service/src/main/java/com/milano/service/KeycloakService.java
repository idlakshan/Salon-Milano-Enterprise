package com.milano.service;

import com.milano.dto.CredentialDTO;
import com.milano.dto.KeycloakUserInfoDTO;
import com.milano.dto.request.SignupRequestDTO;
import com.milano.dto.request.UserRegistrationRequestDTO;
import com.milano.dto.response.KeycloakRoleResponseDTO;
import com.milano.dto.response.KeycloakUserResponseDTO;
import com.milano.dto.response.TokenResponseDTO;
import com.milano.exception.EntryNotFoundException;
import com.milano.exception.KeycloakException;
import com.milano.util.UserMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

@Service
@RequiredArgsConstructor
public class KeycloakService {

    private static final String KEYCLOAK_BASE_URL = "http://localhost:8080";
    private static final String REALM_NAME = "milano-realm";
    private static final String KEYCLOAK_ADMIN_API = KEYCLOAK_BASE_URL + "/admin/realms/" + REALM_NAME + "/users";
    private static final String TOKEN_URL = KEYCLOAK_BASE_URL + "/realms/" + REALM_NAME + "/protocol/openid-connect/token";

    private static final String CLIENT_ID = "salon-booking-client";
    private static final String CLIENT_SECRET = "KhKFi2lQ6wJYRq4u1ZrDMX40HsTMswMV1GRHbCbFGvvrT3Ze1gwzhA5388QIFcn5qYKxXALKW4kJF8bJQYM7RU";
    private static final String GRANT_TYPE = "password";
    private static final String USERNAME = "admin";
    private static final String PASSWORD = "admin123";
    private static final String CLIENT_UUID = "2d3aca7c-9a1f-4179-9035-7d652f62e489";

    private final RestTemplate restTemplate;
    private final UserMapper userMapper;


    public void createUser(SignupRequestDTO signupRequestDTO) {

        String accessToken = getAdminAccessToken(USERNAME, PASSWORD, GRANT_TYPE, null)
                .getAccessToken();

        UserRegistrationRequestDTO userRegistrationRequestDTO = userMapper.toKeycloakUserRequest(signupRequestDTO);

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        headers.setBearerAuth(accessToken);

        HttpEntity<UserRegistrationRequestDTO> requestEntity = new HttpEntity<>(userRegistrationRequestDTO, headers);

        ResponseEntity<Void> response = restTemplate.exchange(
                KEYCLOAK_ADMIN_API,
                HttpMethod.POST,
                requestEntity,
                Void.class
        );

        if (response.getStatusCode() == HttpStatus.CREATED) {
            KeycloakUserResponseDTO user = fetchFirstUserByUsername(signupRequestDTO.getUsername(), accessToken);
            KeycloakRoleResponseDTO role =
                    getRoleByName(CLIENT_UUID, accessToken, signupRequestDTO.getRole().toString());

            List<KeycloakRoleResponseDTO> roles = new ArrayList<>();
            roles.add(role);
            assignRoleToUser(user.getId(), CLIENT_UUID, roles, accessToken);

        } else {
            throw new KeycloakException("Failed to create user in Keycloak. Status: " + response.getStatusCode());
        }
    }


    public TokenResponseDTO getAdminAccessToken(String username, String password, String grantType, String refreshToken) {
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_FORM_URLENCODED);

        MultiValueMap<String, String> body = new LinkedMultiValueMap<>();
        body.add("grant_type", grantType);
        body.add("client_id", CLIENT_ID);
        body.add("client_secret", CLIENT_SECRET);
        body.add("scope", "openid profile email");

        if ("refresh_token".equalsIgnoreCase(grantType)) {
            body.add("refresh_token", refreshToken);
        } else {
            body.add("username", username);
            body.add("password", password);
        }

        HttpEntity<MultiValueMap<String, String>> requestEntity = new HttpEntity<>(body, headers);

        ResponseEntity<TokenResponseDTO> response = restTemplate.exchange(
                TOKEN_URL,
                HttpMethod.POST,
                requestEntity,
                TokenResponseDTO.class
        );

        if (response.getStatusCode() == HttpStatus.OK && response.getBody() != null) {
            return response.getBody();
        } else {
            throw new RuntimeException("Failed to obtain token from Keycloak");
        }
    }

    public KeycloakRoleResponseDTO getRoleByName(String clientUuid, String token, String role) {
        String url = KEYCLOAK_BASE_URL + "/admin/realms/" + REALM_NAME + "/clients/" + clientUuid + "/roles/" + role;

        HttpHeaders headers = new HttpHeaders();
        headers.setBearerAuth(token);

        HttpEntity<Void> requestEntity = new HttpEntity<>(headers);

        ResponseEntity<KeycloakRoleResponseDTO> response = restTemplate.exchange(
                url,
                HttpMethod.GET,
                requestEntity,
                KeycloakRoleResponseDTO.class
        );

        if (response.getStatusCode() == HttpStatus.OK && response.getBody() != null) {
            return response.getBody();
        } else {
            throw new RuntimeException("Failed to fetch role: " + role);
        }
    }


    public KeycloakUserResponseDTO fetchFirstUserByUsername(String username, String token) {
        String url = KEYCLOAK_BASE_URL + "/admin/realms/" + REALM_NAME + "/users?username=" + username + "&exact=true";

        HttpHeaders headers = new HttpHeaders();
        headers.setBearerAuth(token);

        HttpEntity<Void> requestEntity = new HttpEntity<>(headers);

        ResponseEntity<KeycloakUserResponseDTO[]> response = restTemplate.exchange(
                url,
                HttpMethod.GET,
                requestEntity,
                KeycloakUserResponseDTO[].class
        );

        if (response.getStatusCode() == HttpStatus.OK && response.getBody() != null && response.getBody().length > 0) {
            return response.getBody()[0];
        } else {
            throw new EntryNotFoundException("User not found with username: " + username);
        }
    }


    public void assignRoleToUser(String userId, String clientUuid, List<KeycloakRoleResponseDTO> roles, String token) {
        String url = KEYCLOAK_BASE_URL + "/admin/realms/" + REALM_NAME + "/users/" + userId + "/role-mappings/clients/" + clientUuid;

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        headers.setBearerAuth(token);

        HttpEntity<List<KeycloakRoleResponseDTO>> requestEntity = new HttpEntity<>(roles, headers);

        ResponseEntity<Void> response = restTemplate.exchange(
                url,
                HttpMethod.POST,
                requestEntity,
                Void.class
        );

        if (response.getStatusCode() == HttpStatus.NO_CONTENT || response.getStatusCode() == HttpStatus.OK) {
            System.out.println("Role assigned successfully to user ID: " + userId);
        } else {
            throw new KeycloakException("Failed to assign role to user. Status: " + response.getStatusCode());
        }
    }

    public KeycloakUserInfoDTO fetchUserProfileByJwt(String token) {

        String url = KEYCLOAK_BASE_URL + "/realms/" + REALM_NAME + "/protocol/openid-connect/userinfo";

        String cleanToken = token.startsWith("Bearer ") ? token.substring(7) : token;

        HttpHeaders headers = new HttpHeaders();
        headers.setBearerAuth(cleanToken);

        HttpEntity<Void> entity = new HttpEntity<>(headers);

        ResponseEntity<KeycloakUserInfoDTO> response = restTemplate.exchange(
                url,
                HttpMethod.GET,
                entity,
                KeycloakUserInfoDTO.class
        );

        if (response.getStatusCode() == HttpStatus.OK && response.getBody() != null) {
            return response.getBody();
        } else {
            throw new KeycloakException("Failed to fetch user profile from Keycloak");
        }
    }
}