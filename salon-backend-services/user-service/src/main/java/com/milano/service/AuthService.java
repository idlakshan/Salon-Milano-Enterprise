package com.milano.service;

import com.milano.dto.request.SignupRequestDTO;
import com.milano.dto.response.AuthResponseDTO;

public interface AuthService {
    AuthResponseDTO login(String username, String password);
    AuthResponseDTO signup(SignupRequestDTO req);
    AuthResponseDTO getAccessTokenFromRefreshToken(String refreshToken);
}
