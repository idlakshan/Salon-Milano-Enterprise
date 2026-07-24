package com.milano.advisor;

import com.milano.exception.BadCredentialsException;
import com.milano.exception.DuplicateEntryException;
import com.milano.exception.EntryNotFoundException;
import com.milano.exception.KeycloakException;
import com.milano.util.StandardResponseDTO;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.client.HttpClientErrorException;

@RestControllerAdvice
public class AppWiderExceptionHandler {

    @ExceptionHandler(EntryNotFoundException.class)
    public ResponseEntity<StandardResponseDTO> handleEntryNotFoundException(EntryNotFoundException ex) {
        return ResponseEntity
                .status(HttpStatus.NOT_FOUND)
                .body(StandardResponseDTO.builder()
                        .code(HttpStatus.NOT_FOUND.value())
                        .message(ex.getMessage())
                        .data(null)
                        .build());
    }

    @ExceptionHandler(DuplicateEntryException.class)
    public ResponseEntity<StandardResponseDTO> handleDuplicateEntryException(DuplicateEntryException ex) {
        return ResponseEntity
                .status(HttpStatus.CONFLICT)
                .body(StandardResponseDTO.builder()
                        .code(HttpStatus.CONFLICT.value())
                        .message(ex.getMessage())
                        .data(null)
                        .build());
    }

    @ExceptionHandler(BadCredentialsException.class)
    public ResponseEntity<StandardResponseDTO> handleBadCredentialsException(BadCredentialsException ex) {
        return ResponseEntity
                .status(HttpStatus.UNAUTHORIZED)
                .body(StandardResponseDTO.builder()
                        .code(HttpStatus.UNAUTHORIZED.value())
                        .message(ex.getMessage())
                        .data(null)
                        .build());
    }

    @ExceptionHandler(KeycloakException.class)
    public ResponseEntity<StandardResponseDTO> handleKeycloakException(KeycloakException ex) {
        return ResponseEntity
                .status(HttpStatus.BAD_REQUEST)
                .body(StandardResponseDTO.builder()
                        .code(HttpStatus.BAD_REQUEST.value())
                        .message(ex.getMessage())
                        .data(null)
                        .build());
    }

    @ExceptionHandler(HttpClientErrorException.class)
    public ResponseEntity<StandardResponseDTO> handleHttpClientErrorException(HttpClientErrorException ex) {
        HttpStatus status = (HttpStatus) ex.getStatusCode();
        String errorMessage = "Authentication failed";

        if (status == HttpStatus.UNAUTHORIZED || status == HttpStatus.BAD_REQUEST) {
            if (ex.getResponseBodyAsString().contains("invalid_grant")) {
                return ResponseEntity
                        .status(HttpStatus.UNAUTHORIZED)
                        .body(StandardResponseDTO.builder()
                                .code(HttpStatus.UNAUTHORIZED.value())
                                .message("Invalid email or password")
                                .data(null)
                                .build());
            }
        }

        if (status == HttpStatus.CONFLICT) {
            errorMessage = "User with given email or username already exists in Keycloak";
        }

        return ResponseEntity
                .status(status)
                .body(StandardResponseDTO.builder()
                        .code(status.value())
                        .message(errorMessage)
                        .data(null)
                        .build());
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<StandardResponseDTO> handleValidationException(MethodArgumentNotValidException ex) {
        String errorMsg = ex.getBindingResult().getFieldError() != null
                ? ex.getBindingResult().getFieldError().getDefaultMessage()
                : "Validation failed";

        return ResponseEntity
                .status(HttpStatus.BAD_REQUEST)
                .body(StandardResponseDTO.builder()
                        .code(HttpStatus.BAD_REQUEST.value())
                        .message(errorMsg)
                        .data(null)
                        .build());
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<StandardResponseDTO> handleGlobalException(Exception ex) {
        return ResponseEntity
                .status(HttpStatus.INTERNAL_SERVER_ERROR)
                .body(StandardResponseDTO.builder()
                        .code(HttpStatus.INTERNAL_SERVER_ERROR.value())
                        .message("An unexpected error occurred: " + ex.getMessage())
                        .data(null)
                        .build());
    }
}
