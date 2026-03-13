package org.example.taskmanagementsystem.service;

import lombok.RequiredArgsConstructor;
import org.example.taskmanagementsystem.dto.UserRequestDTO; // Thêm DTO mới
import org.example.taskmanagementsystem.dto.UserResponseDTO;
import org.example.taskmanagementsystem.entity.User;
import org.example.taskmanagementsystem.entity.enums.UserRole;
import org.example.taskmanagementsystem.repository.UserRepository;
import org.springframework.security.crypto.password.PasswordEncoder; // Thêm PasswordEncoder
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor // Sẽ tự tạo Constructor cho userRepository và passwordEncoder
public class UserService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder; // 1. Tiêm PasswordEncoder vào

    // 2. XÓA Constructor thủ công cũ để tránh lỗi trùng lặp với @RequiredArgsConstructor

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

    // 3. Thêm hàm Đăng ký để lưu User với mật khẩu đã mã hóa
    public UserResponseDTO register(UserRequestDTO dto) {
        if (userRepository.findByUsername(dto.getUsername()).isPresent()) {
            throw new IllegalArgumentException("Username đã tồn tại!");
        }

        User user = new User();
        user.setUsername(dto.getUsername());
        user.setFullName(dto.getFullName());
        user.setRole(UserRole.ROLE_USER);

        // QUAN TRỌNG: Mã hóa mật khẩu trước khi lưu
        user.setPassword(passwordEncoder.encode(dto.getPassword()));

        User saved = userRepository.save(user);

        UserResponseDTO response = new UserResponseDTO();
        response.setId(saved.getId());
        response.setUsername(saved.getUsername());
        response.setFullName(saved.getFullName());
        response.setRole(saved.getRole().name());
        return response;
    }
}