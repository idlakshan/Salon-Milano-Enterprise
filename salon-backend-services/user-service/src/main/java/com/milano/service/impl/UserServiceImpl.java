package com.milano.service.impl;

import com.milano.dto.request.UserRequestDTO;
import com.milano.dto.response.PagedResponseDTO;
import com.milano.dto.response.UserResponseDTO;
import com.milano.entity.User;
import com.milano.exception.EntryNotFoundException;
import com.milano.repo.UserRepo;
import com.milano.service.UserService;
import com.milano.util.UserMapper;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {

    private final UserRepo userRepo;
    private final UserMapper userMapper;

    @Override
    public void createUser(UserRequestDTO userRequestDTO) {
        userRepo.save(userMapper.toUser(userRequestDTO));
    }

    @Override
    public void updateUser(UserRequestDTO userRequestDTO, UUID id) {
        User user = userRepo.findById(id)
                .orElseThrow(() -> new EntryNotFoundException("User not found for provided id"));

        user.setFullName(userRequestDTO.getFullName());
        user.setUserName(userRequestDTO.getUsername());
        user.setEmail(userRequestDTO.getEmail());
        user.setPhone(userRequestDTO.getPhone());
        user.setRole(userRequestDTO.getRole());
        userRepo.save(user);

    }

    @Override
    public void deleteUser(UUID id) {
        userRepo.findById(id)
                .orElseThrow(() -> new EntryNotFoundException("User not found for provided id"));
        userRepo.deleteById(id);

    }

    @Override
    public UserResponseDTO findUserById(UUID id) {
        User user = userRepo.findById(id)
                .orElseThrow(() -> new EntryNotFoundException("User not found for provided id"));
        return userMapper.toUserResponseDTO(user);
    }

    @Override
    public PagedResponseDTO<UserResponseDTO> findUsers(String searchText, int page, int size) {
        searchText= "%"+searchText+"%";

        return PagedResponseDTO.<UserResponseDTO>builder()
                .dataList(
                        userRepo.findAllUsers(searchText, PageRequest.of(page, size))
                                .stream().map(userMapper::toUserResponseDTO).toList())
                .dataCount(
                        userRepo.countAllUsers(searchText)
                ).build();
    }
}
