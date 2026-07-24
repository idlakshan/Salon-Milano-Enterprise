package com.milano.api;

import com.milano.dto.request.LoginRequestDTO;
import com.milano.dto.request.SignupRequestDTO;
import com.milano.dto.response.AuthResponseDTO;
import com.milano.service.AuthService;
import com.milano.util.StandardResponseDTO;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@Slf4j
@RestController
@RequestMapping("api/v1/auth")
@RequiredArgsConstructor
public class AuthController {

    private final AuthService authService;

    @PostMapping("/signup")
    public ResponseEntity<StandardResponseDTO> signupHandler(
            @Valid @RequestBody SignupRequestDTO req) {

        AuthResponseDTO response = authService.signup(req);

        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(StandardResponseDTO.builder()
                        .code(HttpStatus.CREATED.value())
                        .message("User created successfully")
                        .data(response)
                        .build());
    }


    @PostMapping("/login")
    public ResponseEntity<StandardResponseDTO> loginHandler(
            @Valid @RequestBody LoginRequestDTO req) {

        log.info("Received login request for username: {}", req.getEmail());
        AuthResponseDTO response = authService.login(req.getEmail(), req.getPassword());

        return ResponseEntity
                .status(HttpStatus.OK)
                .body(StandardResponseDTO.builder()
                        .code(HttpStatus.OK.value())
                        .message("User logged in successfully")
                        .data(response)
                        .build());
    }


    @GetMapping("/access-token/refresh-token/{refreshToken}")
    public ResponseEntity<StandardResponseDTO> getAccessTokenHandler(
            @PathVariable String refreshToken) {

        AuthResponseDTO response = authService.getAccessTokenFromRefreshToken(refreshToken);

        return ResponseEntity
                .status(HttpStatus.OK)
                .body(StandardResponseDTO.builder()
                        .code(HttpStatus.OK.value())
                        .message("Access token refreshed successfully")
                        .data(response)
                        .build());
    }
}