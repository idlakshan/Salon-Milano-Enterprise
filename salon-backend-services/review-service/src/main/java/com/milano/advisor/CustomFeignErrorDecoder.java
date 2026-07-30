package com.milano.advisor;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.milano.exception.EntryNotFoundException;
import com.milano.exception.UnauthorizedException;
import com.milano.util.StandardResponseDTO;
import feign.Response;
import feign.codec.ErrorDecoder;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.io.InputStream;

@Slf4j
@Component
public class CustomFeignErrorDecoder implements ErrorDecoder {

    private final ErrorDecoder defaultErrorDecoder = new Default();

    @Override
    public Exception decode(String methodKey, Response response) {
        StandardResponseDTO responseDTO = null;

        try (InputStream bodyIs = response.body().asInputStream()) {
            ObjectMapper mapper = new ObjectMapper();
            responseDTO = mapper.readValue(bodyIs, StandardResponseDTO.class);
        } catch (Exception e) {
            log.error("Error decoding response body from Feign Client: {}", e.getMessage());
        }


        String message = (responseDTO != null && responseDTO.getMessage() != null)
                ? responseDTO.getMessage()
                : "Error occurred while calling downstream service";

        switch (response.status()) {
            case 404:
                return new EntryNotFoundException(message);

            case 401:
            case 403:
                return new UnauthorizedException(message);

            case 500:
                return new RuntimeException("Downstream Service Error: " + message);

            default:
                return defaultErrorDecoder.decode(methodKey, response);
        }
    }
}