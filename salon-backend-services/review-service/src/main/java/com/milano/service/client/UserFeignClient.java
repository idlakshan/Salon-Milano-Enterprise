package com.milano.service.client;

import com.milano.util.StandardResponseDTO;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestHeader;

import java.util.UUID;

@FeignClient(name = "USER-SERVICE", path = "/api/v1/users")
public interface UserFeignClient {

    @GetMapping("/{id}")
    ResponseEntity<StandardResponseDTO> findCustomerById(@PathVariable("id") UUID id);

    @GetMapping("/profile")
    ResponseEntity<StandardResponseDTO> getUserProfile(@RequestHeader("Authorization") String jwt);

}
