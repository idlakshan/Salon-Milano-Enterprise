package com.milano.service.client;

import com.milano.dto.request.BookingRequestDTO;
import com.milano.dto.response.BookingResponseDTO;
import com.milano.entity.PAYMENT_METHOD;
import com.milano.util.StandardResponseDTO;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestParam;

@FeignClient(name = "PAYMENT-SERVICE", path = "/api/v1/payments")
public interface PaymentFeignClient {

    @PostMapping("/create")
    ResponseEntity<StandardResponseDTO> createPaymentLink(
            @RequestBody BookingResponseDTO bookingDTO,
            @RequestParam PAYMENT_METHOD paymentMethod,
            @RequestHeader("Authorization") String jwt);

}
