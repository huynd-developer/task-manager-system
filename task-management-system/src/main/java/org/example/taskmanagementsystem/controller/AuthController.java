package org.example.taskmanagementsystem.controller;

import lombok.RequiredArgsConstructor;
import org.example.taskmanagementsystem.dto.ApiResponse;
import org.example.taskmanagementsystem.dto.UserRequestDTO;
import org.example.taskmanagementsystem.dto.UserResponseDTO;
import org.example.taskmanagementsystem.security.JwtTokenProvider;
import org.example.taskmanagementsystem.service.UserService;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/auth")
@RequiredArgsConstructor
public class AuthController {

    private final UserService userService;
    private final AuthenticationManager authenticationManager;
    private final JwtTokenProvider tokenProvider;

    @PostMapping("/register")
    public ApiResponse<UserResponseDTO> register(@RequestBody UserRequestDTO dto) {
        return ApiResponse.<UserResponseDTO>builder()
                .status(201)
                .message("Đăng ký tài khoản thành công")
                .data(userService.register(dto))
                .build();
    }

    @PostMapping("/login")
    public ApiResponse<String> login(@RequestBody UserRequestDTO dto) {
        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(dto.getUsername(), dto.getPassword())
        );

        String token = tokenProvider.generateToken(dto.getUsername());

        return ApiResponse.<String>builder()
                .status(200)
                .message("Đăng nhập thành công")
                .data(token)
                .build();
    }
}