package com.milano.util;

import com.milano.dto.request.UserRequestDTO;
import com.milano.dto.response.UserResponseDTO;
import com.milano.entity.User;
import org.springframework.stereotype.Component;

@Component
public class UserMapper {

    public User toUser(UserRequestDTO userRequestDTO){
        return User.builder().fullName(userRequestDTO.getFullName())
                .userName(userRequestDTO.getUsername())
                .email(userRequestDTO.getEmail())
                .phone(userRequestDTO.getPhone()).
                role(userRequestDTO.getRole())
                .build();
    }

    public UserResponseDTO toUserResponseDTO(User user){
        return UserResponseDTO.builder().id(user.getId())
                .fullName(user.getFullName())
                .username(user.getUserName())
                .email(user.getEmail())
                .phone(user.getPhone())
                .role(user.getRole())
                .createdAt(user.getCreatedAt())
                .updatedAt(user.getUpdatedAt())
                .build();
    }

}
