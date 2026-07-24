package com.milano.service;

import com.milano.dto.request.UserRequestDTO;
import com.milano.dto.response.PagedResponseDTO;
import com.milano.dto.response.UserResponseDTO;

import java.util.List;
import java.util.UUID;

public interface UserService {
    void createUser(UserRequestDTO userRequestDTO);
    void updateUser(UserRequestDTO userRequestDTO, UUID id);
    void deleteUser(UUID id);
    UserResponseDTO findUserById(UUID id);
    PagedResponseDTO<UserResponseDTO> findUsers(String searchText, int page, int size);
    UserResponseDTO getUserFromJwt(String jwt);


}
