package org.example.taskmanagementsystem.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.example.taskmanagementsystem.dto.ApiResponse;
import org.example.taskmanagementsystem.dto.UserResponseDTO;
import org.example.taskmanagementsystem.service.UserService;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/users")
@Tag(name = "4. User Management", description = "Các API quản lý thông tin người dùng")
public class UserController {

    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping
    @PreAuthorize("hasRole('ADMIN')")
    @Operation(summary = "Lấy danh sách người dùng", description = "Trả về danh sách tất cả người dùng trong hệ thống (Yêu cầu quyền ADMIN)")
    public ResponseEntity<ApiResponse<List<UserResponseDTO>>> getAllUsers() {
        return ResponseEntity.ok(ApiResponse.<List<UserResponseDTO>>builder()
                .status(200)
                .message("Lấy danh sách người dùng thành công")
                .data(userService.getAllUsers())
                .build());
    }
}