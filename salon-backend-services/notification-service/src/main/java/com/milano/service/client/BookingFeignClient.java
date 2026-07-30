package com.milano.service.client;

import com.milano.util.StandardResponseDTO;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.UUID;

@FeignClient(name = "BOOKING-SERVICE", path = "/api/v1/bookings")
public interface BookingFeignClient {

    @GetMapping("/{id}")
    ResponseEntity<StandardResponseDTO> getBookingById(@PathVariable UUID id);
}
