package com.milano.api;

import com.milano.dto.request.UserRequestDTO;
import com.milano.dto.response.PagedResponseDTO;
import com.milano.dto.response.UserResponseDTO;
import com.milano.service.UserService;
import com.milano.util.StandardResponseDTO;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
@RequestMapping("api/v1/users")
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;

    @PostMapping
    public ResponseEntity<StandardResponseDTO> createUser(@RequestBody @Valid UserRequestDTO userRequestDTO) {
        userService.createUser(userRequestDTO);
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(StandardResponseDTO.builder().code(201).message("User Saved Successfully").data(null).build());
    }

    @GetMapping("/profile")
    public ResponseEntity<StandardResponseDTO> getUserProfile(@RequestHeader("Authorization") String jwt) {

        UserResponseDTO userFromJwt = userService.getUserFromJwt(jwt);

        return ResponseEntity.status(HttpStatus.OK)
                .body(StandardResponseDTO.builder()
                        .code(HttpStatus.OK.value())
                        .message("User profile retrieved successfully")
                        .data(userFromJwt)
                        .build());
    }

    @PutMapping("/{id}")
    public ResponseEntity<StandardResponseDTO> updateUser(@RequestBody @Valid UserRequestDTO userRequestDTO,
                                                          @PathVariable UUID id) {
        userService.updateUser(userRequestDTO, id);
        return ResponseEntity.status(HttpStatus.OK)
                .body(StandardResponseDTO.builder().code(200).message("User updated Successfully").data(null).build());
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<StandardResponseDTO> deleteCustomer(@PathVariable UUID id) {
        userService.deleteUser(id);
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(StandardResponseDTO.builder()
                        .code(200)
                        .message("User deleted successfully")
                        .data(null)
                        .build());
    }

    @GetMapping("/{id}")
    public ResponseEntity<StandardResponseDTO> findCustomerById(@PathVariable UUID id) {
        UserResponseDTO user = userService.findUserById(id);
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(StandardResponseDTO.builder()
                        .code(200)
                        .message("User retrieved successfully")
                        .data(user)
                        .build());
    }

    @GetMapping
    public ResponseEntity<StandardResponseDTO> searchCustomers(
            @RequestParam(defaultValue = "") String searchText,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size) {
        PagedResponseDTO<UserResponseDTO> result = userService.findUsers(searchText, page, size);
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(StandardResponseDTO.builder()
                        .code(200)
                        .message("Customers retrieved successfully")
                        .data(result)
                        .build());
    }

}

