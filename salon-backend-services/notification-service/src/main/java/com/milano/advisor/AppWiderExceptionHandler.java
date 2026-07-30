package com.milano.advisor;

import com.milano.exception.EntryNotFoundException;
import com.milano.exception.PaymentException;
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

    @ExceptionHandler(PaymentException.class)
    public ResponseEntity<StandardResponseDTO> handlePaymentException(PaymentException ex) {

        return ResponseEntity
                .status(HttpStatus.BAD_REQUEST)
                .body(StandardResponseDTO.builder()
                        .code(400)
                        .message(ex.getMessage())
                        .data(null)
                        .build());
    }
}
