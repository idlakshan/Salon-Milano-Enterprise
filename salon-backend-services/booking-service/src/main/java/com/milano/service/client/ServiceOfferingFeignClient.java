package com.milano.service.client;

import com.milano.util.StandardResponseDTO;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.Set;
import java.util.UUID;

@FeignClient(name = "OFFERING-SERVICE", path = "/api/v1/service-offering")
public interface ServiceOfferingFeignClient {

    @GetMapping("/list/{ids}")
    ResponseEntity<StandardResponseDTO> getServicesByIds(@PathVariable("ids") Set<UUID> ids);
}
