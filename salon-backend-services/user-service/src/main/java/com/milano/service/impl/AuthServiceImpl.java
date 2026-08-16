package com.milano.service.impl;

import com.milano.dto.request.SignupRequestDTO;
import com.milano.dto.response.AuthResponseDTO;
import com.milano.dto.keycloak.TokenResponseDTO;
import com.milano.entity.User;
import com.milano.exception.DuplicateEntryException;
import com.milano.exception.EntryNotFoundException;
import com.milano.repo.UserRepo;
import com.milano.service.AuthService;
import com.milano.util.UserMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;


@Service
@RequiredArgsConstructor
public class AuthServiceImpl implements AuthService {

    private final UserRepo userRepo;
    private final KeycloakServiceImpl keycloakService;
    private final UserMapper userMapper;

    @Override
    public AuthResponseDTO login(String email, String password) {
        TokenResponseDTO tokenResponse = keycloakService.getAdminAccessToken(
                email,
                password,
                "password",
                null
        );

        User user = userRepo.findByEmail(email)
                .orElseThrow(() -> new EntryNotFoundException("User not found with email: " + email));

        return userMapper.toAuthResponse(
                "Welcome Back " + email,
                "login success",
                tokenResponse,
                user.getRole()
        );
    }

    @Override
    public AuthResponseDTO signup(SignupRequestDTO req) {

        if (userRepo.existsByEmail(req.getEmail())) {
            throw new DuplicateEntryException("User with email " + req.getEmail() + " already exists in database");
        }
        keycloakService.createUser(req);

        User user = userMapper.toUserEntity(req);
        userRepo.save(user);

        TokenResponseDTO tokenResponse = keycloakService.getAdminAccessToken(
                req.getUsername(),
                req.getPassword(),
                "password",
                null
        );


        return userMapper.toAuthResponse(
                "Welcome " + user.getEmail(),
                "Register success",
                tokenResponse,
                user.getRole() != null ? user.getRole() : req.getRole()
        );
    }

    @Override
    public AuthResponseDTO getAccessTokenFromRefreshToken(String refreshToken) {
        TokenResponseDTO tokenResponse = keycloakService.getAdminAccessToken(
                null,
                null,
                "refresh_token",
                refreshToken
        );

        return userMapper.toAuthResponse(
                null,
                "Access token received",
                tokenResponse,
                null
        );
    }
}
