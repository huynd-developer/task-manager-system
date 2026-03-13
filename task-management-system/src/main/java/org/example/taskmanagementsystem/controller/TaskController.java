package org.example.taskmanagementsystem.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.example.taskmanagementsystem.dto.ApiResponse;
import org.example.taskmanagementsystem.service.TaskService;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import jakarta.validation.Valid;
import org.example.taskmanagementsystem.dto.TaskRequestDTO;
import org.example.taskmanagementsystem.dto.TaskResponseDTO;
import org.springframework.http.ResponseEntity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import java.util.List;
import java.security.Principal;
@RestController
@RequestMapping("/api/tasks")
@Tag(name = "2. Task Management", description = "Các API quản lý công việc (Tạo, sửa, xóa, lấy danh sách)")
public class TaskController {

    private final TaskService taskService;

    public TaskController(TaskService taskService) {
        this.taskService = taskService;
    }

    @GetMapping
    @PreAuthorize("hasRole('ADMIN')")
    @Operation(summary = "Lấy danh sách tất cả Task (Phân trang)", description = "Chỉ ADMIN mới có quyền xem toàn bộ Task trong hệ thống")
    public ResponseEntity<ApiResponse<Page<TaskResponseDTO>>> getAllTasks(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size) {
        Pageable pageable = PageRequest.of(page, size);
        return ResponseEntity.ok(ApiResponse.<Page<TaskResponseDTO>>builder()
                .status(200)
                .data(taskService.getAllTasks(pageable))
                .build());
    }

    @GetMapping("/status")
    @Operation(summary = "Lọc công việc theo trạng thái", description = "Các trạng thái hợp lệ: TODO, IN_PROGRESS, DONE, CANCELED")
    public ResponseEntity<ApiResponse<List<TaskResponseDTO>>> getByStatus(@RequestParam String value) {
        return ResponseEntity.ok(ApiResponse.<List<TaskResponseDTO>>builder()
                .status(200)
                .message("Lọc công việc theo trạng thái thành công")
                .data(taskService.getTasksByStatus(value))
                .build());
    }

    @GetMapping("/user/{id}")
    @PreAuthorize("hasAnyRole('ADMIN', 'USER')")
    @Operation(summary = "Lấy công việc theo người dùng", description = "Lấy danh sách các công việc được gán cho một User cụ thể")
    public ResponseEntity<ApiResponse<List<TaskResponseDTO>>> getByUser(@PathVariable Long id) {
        return ResponseEntity.ok(ApiResponse.<List<TaskResponseDTO>>builder()
                .status(200)
                .message("Lấy công việc theo người dùng thành công")
                .data(taskService.getTasksByUser(id))
                .build());
    }

    @GetMapping("/project/{id}")
    @Operation(summary = "Lấy công việc theo dự án", description = "Lấy danh sách toàn bộ công việc thuộc về một Dự án cụ thể")
    public ResponseEntity<ApiResponse<List<TaskResponseDTO>>> getByProject(@PathVariable Long id) {
        return ResponseEntity.ok(ApiResponse.<List<TaskResponseDTO>>builder()
                .status(200)
                .message("Lấy công việc theo dự án thành công")
                .data(taskService.getTasksByProject(id))
                .build());
    }

    @PostMapping
    @PreAuthorize("hasAnyRole('ADMIN', 'USER')")
    @Operation(summary = "Tạo mới một công việc", description = "Kiểm tra User phải thuộc Project. Validate Deadline (dueDate) phải lớn hơn hoặc bằng ngày hiện tại")
    public ResponseEntity<ApiResponse<TaskResponseDTO>> createTask(@Valid @RequestBody TaskRequestDTO dto) {
        return ResponseEntity.status(201).body(ApiResponse.<TaskResponseDTO>builder()
                .status(201)
                .message("Tạo công việc thành công")
                .data(taskService.createTask(dto))
                .build());
    }

    @PutMapping("/{id}/status")
    @Operation(summary = "Cập nhật trạng thái công việc", description = "Chặn cập nhật nếu Task đã ở trạng thái DONE hoặc CANCELED")
    public ResponseEntity<ApiResponse<TaskResponseDTO>> updateStatus(@PathVariable Long id, @RequestParam String status) {
        return ResponseEntity.ok(ApiResponse.<TaskResponseDTO>builder()
                .status(200)
                .message("Cập nhật trạng thái thành công")
                .data(taskService.updateTaskStatus(id, status))
                .build());
    }

    @DeleteMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    @Operation(summary = "Xóa công việc", description = "Chỉ ADMIN mới có quyền xóa công việc khỏi hệ thống")
    public ResponseEntity<ApiResponse<Void>> deleteTask(@PathVariable Long id) {
        taskService.deleteTask(id);
        return ResponseEntity.ok(ApiResponse.<Void>builder()
                .status(200)
                .message("Xóa công việc thành công")
                .build());
    }
    @GetMapping("/my-tasks")
    @PreAuthorize("hasRole('USER')")
    @Operation(summary = "Lấy công việc của TÔI", description = "Lấy danh sách task của user đang đăng nhập (tự động nhận diện qua JWT Token)")
    public ResponseEntity<ApiResponse<List<TaskResponseDTO>>> getMyTasks(Principal principal) {
        String username = principal.getName();

        return ResponseEntity.ok(ApiResponse.<List<TaskResponseDTO>>builder()
                .status(200)
                .message("Lấy danh sách công việc của tôi thành công")
                .data(taskService.getTasksByUsername(username))
                .build());
    }
}