package org.example.taskmanagementsystem.service;

import org.example.taskmanagementsystem.dto.UserResponseDTO;
import org.example.taskmanagementsystem.entity.User;
import org.example.taskmanagementsystem.repository.UserRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserService {

    private final UserRepository userRepository;

    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public List<UserResponseDTO> getAllUsers() {
        return userRepository.findAll()
                .stream()
                .map(user -> {
                    UserResponseDTO dto = new UserResponseDTO();
                    dto.setId(user.getId());
                    dto.setUsername(user.getUsername());
                    dto.setFullName(user.getFullName());
                    dto.setRole(user.getRole().name());
                    return dto;
                })
                .toList();
    }

}
