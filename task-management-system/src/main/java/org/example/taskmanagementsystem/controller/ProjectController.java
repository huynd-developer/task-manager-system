package org.example.taskmanagementsystem.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.example.taskmanagementsystem.dto.ApiResponse;
import org.example.taskmanagementsystem.dto.ProjectRequestDTO;
import org.example.taskmanagementsystem.dto.ProjectResponseDTO;
import org.example.taskmanagementsystem.service.ProjectService;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/projects")
@PreAuthorize("hasRole('ADMIN')")
@Tag(name = "3. Project Management", description = "Các API quản lý dự án (Tất cả API này đều yêu cầu quyền ADMIN)")
public class ProjectController {

    private final ProjectService projectService;

    public ProjectController(ProjectService projectService) {
        this.projectService = projectService;
    }

    @GetMapping
    @Operation(summary = "Lấy danh sách dự án", description = "Trả về toàn bộ danh sách dự án có trong hệ thống")
    public ResponseEntity<ApiResponse<List<ProjectResponseDTO>>> getAllProjects() {
        return ResponseEntity.ok(ApiResponse.<List<ProjectResponseDTO>>builder()
                .status(200)
                .message("Lấy danh sách dự án thành công")
                .data(projectService.getAllProjects())
                .build());
    }

    @PostMapping
    @Operation(summary = "Tạo mới dự án", description = "Tạo dự án mới và gán các thành viên (User) vào dự án thông qua danh sách memberIds")
    public ResponseEntity<ApiResponse<ProjectResponseDTO>> createProject(@Valid @RequestBody ProjectRequestDTO projectDto) {
        return ResponseEntity.status(201).body(ApiResponse.<ProjectResponseDTO>builder()
                .status(201)
                .message("Tạo dự án thành công")
                .data(projectService.createProject(projectDto))
                .build());
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "Xóa dự án (Cascade)", description = "Xóa dự án sẽ tự động xóa tất cả các Task và Liên kết thành viên thuộc về dự án đó")
    public ResponseEntity<ApiResponse<Void>> deleteProject(@PathVariable Long id) {
        projectService.deleteProject(id);
        return ResponseEntity.ok(ApiResponse.<Void>builder()
                .status(200)
                .message("Xóa dự án và các dữ liệu liên quan thành công")
                .build());
    }
}