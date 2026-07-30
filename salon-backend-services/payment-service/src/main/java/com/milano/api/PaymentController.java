package com.milano.api;

import com.milano.dto.request.BookingRequestDTO;
import com.milano.dto.response.PaymentOrderResponseDTO;
import com.milano.dto.response.PaymentResponseDTO;
import com.milano.entity.PAYMENT_METHOD;
import com.milano.service.PaymentService;
import com.milano.util.StandardResponseDTO;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
@RequestMapping("api/v1/payments")
@RequiredArgsConstructor
public class PaymentController {

    private final PaymentService paymentService;

    @PostMapping("/create")
    public ResponseEntity<StandardResponseDTO> createPaymentLink(
            @RequestBody BookingRequestDTO bookingRequestDTO,
            @RequestParam PAYMENT_METHOD paymentMethod,
            @RequestHeader("Authorization") String jwt) {


//        UserRequestDTO user = new UserRequestDTO();
//        user.setId(UUID.fromString("550e8400-e29b-41d4-a716-446655440000"));
//        user.setFullName("Dimuthu Lakshan");
//        user.setEmail("dimuthu@gmail.com");

        PaymentResponseDTO order = paymentService.createOrder(jwt, bookingRequestDTO, paymentMethod);

        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(StandardResponseDTO.builder()
                        .code(201)
                        .message("Payment created successfully")
                        .data(order)
                        .build());
    }

    @GetMapping("/{paymentOrderId}")
    public ResponseEntity<StandardResponseDTO> getPaymentOrderId(@PathVariable UUID paymentOrderId) {

        PaymentOrderResponseDTO paymentOrderById = paymentService.getPaymentOrderById(paymentOrderId);

        return ResponseEntity
                .status(HttpStatus.OK)
                .body(StandardResponseDTO.builder()
                        .code(200)
                        .message("Payment retrieve successfully")
                        .data(paymentOrderById)
                        .build());
    }

    @PatchMapping("/proceed")
    public ResponseEntity<StandardResponseDTO> proceedPayment(
            @RequestParam String paymentId,
            @RequestParam String paymentLinkId) {


        boolean success = paymentService.proceedPayment(paymentId, paymentLinkId);

        return ResponseEntity
                .status(HttpStatus.OK)
                .body(StandardResponseDTO.builder()
                        .code(200)
                        .message(success ? "Payment completed successfully" : "Payment failed")
                        .data(success)
                        .build());
    }
}
