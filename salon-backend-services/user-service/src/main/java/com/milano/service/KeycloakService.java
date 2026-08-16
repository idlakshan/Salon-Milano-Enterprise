package com.milano.service;

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
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class KeycloakService {

    @Value("${keycloak.base-url}")
    private String keycloakBaseUrl;

    @Value("${keycloak.realm}")
    private String realmName;

    @Value("${keycloak.client-id}")
    private String clientId;

    @Value("${keycloak.client-secret}")
    private String clientSecret;

    @Value("${keycloak.client-uuid}")
    private String clientUuid;

    @Value("${keycloak.admin.username}")
    private String adminUsername;

    @Value("${keycloak.admin.password}")
    private String adminPassword;

    private static final String GRANT_TYPE = "password";

    private final RestTemplate restTemplate;
    private final UserMapper userMapper;


    private String getKeycloakAdminApiUrl() {
        return keycloakBaseUrl + "/admin/realms/" + realmName + "/users";
    }

    private String getTokenUrl() {
        return keycloakBaseUrl + "/realms/" + realmName + "/protocol/openid-connect/token";
    }


    public void createUser(SignupRequestDTO signupRequestDTO) {

        String accessToken = getAdminAccessToken(adminUsername, adminPassword, GRANT_TYPE, null)
                .getAccessToken();

        UserRegistrationRequestDTO userRegistrationRequestDTO = userMapper.toKeycloakUserRequest(signupRequestDTO);

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        headers.setBearerAuth(accessToken);

        HttpEntity<UserRegistrationRequestDTO> requestEntity = new HttpEntity<>(userRegistrationRequestDTO, headers);

        ResponseEntity<Void> response = restTemplate.exchange(
                getKeycloakAdminApiUrl(),
                HttpMethod.POST,
                requestEntity,
                Void.class
        );

        if (response.getStatusCode() == HttpStatus.CREATED) {
            KeycloakUserResponseDTO user = fetchFirstUserByUsername(signupRequestDTO.getUsername(), accessToken);
            KeycloakRoleResponseDTO role =
                    getRoleByName(clientUuid, accessToken, signupRequestDTO.getRole().toString());

            List<KeycloakRoleResponseDTO> roles = new ArrayList<>();
            roles.add(role);
            assignRoleToUser(user.getId(), clientUuid, roles, accessToken);

        } else {
            throw new KeycloakException("Failed to create user in Keycloak. Status: " + response.getStatusCode());
        }
    }


    public TokenResponseDTO getAdminAccessToken(String username, String password, String grantType, String refreshToken) {
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_FORM_URLENCODED);

        MultiValueMap<String, String> body = new LinkedMultiValueMap<>();
        body.add("grant_type", grantType);
        body.add("client_id", clientId);
        body.add("client_secret", clientSecret);
        body.add("scope", "openid profile email");

        if ("refresh_token".equalsIgnoreCase(grantType)) {
            body.add("refresh_token", refreshToken);
        } else {
            body.add("username", username);
            body.add("password", password);
        }

        HttpEntity<MultiValueMap<String, String>> requestEntity = new HttpEntity<>(body, headers);

        ResponseEntity<TokenResponseDTO> response = restTemplate.exchange(
                getTokenUrl(),
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
        String url = keycloakBaseUrl + "/admin/realms/" + realmName + "/clients/" + clientUuid + "/roles/" + role;

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
        String url = keycloakBaseUrl + "/admin/realms/" + realmName + "/users?username=" + username + "&exact=true";

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
        String url = keycloakBaseUrl + "/admin/realms/" + realmName + "/users/" + userId + "/role-mappings/clients/" + clientUuid;

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

        String url = keycloakBaseUrl + "/realms/" + realmName + "/protocol/openid-connect/userinfo";

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