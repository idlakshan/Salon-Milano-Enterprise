package com.milano.advisor;

import com.milano.exception.EntryNotFoundException;
import com.milano.exception.UnauthorizedException;
import com.milano.exception.ValidationException;
import com.milano.util.StandardResponseDTO;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class AppWiderExceptionHandler {

    @ExceptionHandler(EntryNotFoundException.class)
    public ResponseEntity<StandardResponseDTO> handleEntryNotFoundException(EntryNotFoundException ex) {
        return ResponseEntity
                .status(HttpStatus.NOT_FOUND)
                .body(StandardResponseDTO.builder()
                        .code(404)
                        .message(ex.getMessage())
                        .data(null)
                        .build());
    }

    @ExceptionHandler(ValidationException.class)
    public ResponseEntity<StandardResponseDTO> handleValidationException(ValidationException ex) {
        return ResponseEntity
                .status(HttpStatus.BAD_REQUEST)
                .body(StandardResponseDTO.builder()
                        .code(400)
                        .message(ex.getMessage())
                        .data(ex)
                        .build());
    }

    @ExceptionHandler(UnauthorizedException.class)
    public ResponseEntity<StandardResponseDTO> handleUnauthorizedException(UnauthorizedException ex) {
        return ResponseEntity
                .status(HttpStatus.FORBIDDEN)
                .body(StandardResponseDTO.builder()
                        .code(403)
                        .message(ex.getMessage())
                        .data(null)
                        .build());
    }
}
