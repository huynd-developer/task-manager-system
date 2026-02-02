package org.example.taskmanagementsystem.controller;

import org.example.taskmanagementsystem.service.TaskService;
import org.springframework.web.bind.annotation.*;
import jakarta.validation.Valid;
import org.example.taskmanagementsystem.dto.TaskRequestDTO;
import org.example.taskmanagementsystem.dto.TaskResponseDTO;
import org.springframework.http.ResponseEntity;

import java.util.List;
@RestController
@RequestMapping("/api/tasks")
public class TaskController {

    private final TaskService taskService;

    public TaskController(TaskService taskService) {
        this.taskService = taskService;
    }

    // GET /api/tasks
    @GetMapping
    public ResponseEntity<List<TaskResponseDTO>> getAllTasks() {
        return ResponseEntity.ok(taskService.getAllTasks());
    }

    // GET /api/tasks/status?value=TODO
    @GetMapping("/status")
    public ResponseEntity<List<TaskResponseDTO>> getByStatus(
            @RequestParam String value) {
        return ResponseEntity.ok(taskService.getTasksByStatus(value));
    }

    // GET /api/tasks/user/{id}
    @GetMapping("/user/{id}")
    public ResponseEntity<List<TaskResponseDTO>> getByUser(
            @PathVariable Long id) {
        return ResponseEntity.ok(taskService.getTasksByUser(id));
    }

    // GET /api/tasks/project/{id}
    @GetMapping("/project/{id}")
    public ResponseEntity<List<TaskResponseDTO>> getByProject(
            @PathVariable Long id) {
        return ResponseEntity.ok(taskService.getTasksByProject(id));
    }

    // POST /api/tasks
    @PostMapping
    public ResponseEntity<TaskResponseDTO> createTask(
            @Valid @RequestBody TaskRequestDTO dto) {
        return ResponseEntity.status(201)
                .body(taskService.createTask(dto));
    }

    // PUT /api/tasks/{id}/status?status=DONE
    @PutMapping("/{id}/status")
    public ResponseEntity<TaskResponseDTO> updateStatus(
            @PathVariable Long id,
            @RequestParam String status) {
        return ResponseEntity.ok(taskService.updateTaskStatus(id, status));
    }
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteTask(@PathVariable Long id) {
        taskService.deleteTask(id);
        return ResponseEntity.noContent().build();
    }

}
