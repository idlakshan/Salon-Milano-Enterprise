package com.milano.service.client;

import com.milano.util.StandardResponseDTO;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestHeader;

@FeignClient(value = "SALON-SERVICE", path = "/api/v1/salons")
public interface SalonFeignClient {

    @GetMapping("/owner")
    ResponseEntity<StandardResponseDTO> findSalonByOwnerId(@RequestHeader("Authorization") String jwt);
}
