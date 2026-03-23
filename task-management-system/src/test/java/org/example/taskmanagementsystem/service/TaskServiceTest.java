package org.example.taskmanagementsystem.service;

import org.example.taskmanagementsystem.dto.TaskRequestDTO;
import org.example.taskmanagementsystem.entity.Project;
import org.example.taskmanagementsystem.entity.Task;
import org.example.taskmanagementsystem.entity.User;
import org.example.taskmanagementsystem.entity.enums.TaskStatus;
import org.example.taskmanagementsystem.exception.ResourceNotFoundException;
import org.example.taskmanagementsystem.repository.ProjectRepository;
import org.example.taskmanagementsystem.repository.TaskRepository;
import org.example.taskmanagementsystem.repository.UserRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class TaskServiceTest {
    @Mock private TaskRepository taskRepository;
    @Mock private ProjectRepository projectRepository;
    @Mock private UserRepository userRepository;
    @InjectMocks private TaskService taskService;

    // --- HELPER METHOD TẠO TASK MẪU ---
    private Task createValidTask() {
        Task task = new Task();
        task.setId(1L);
        task.setTitle("Học Spring Boot");
        task.setStatus(TaskStatus.TODO);
        task.setPriority(org.example.taskmanagementsystem.entity.enums.Priority.HIGH);
        return task;
    }

    // ================== CÁC TEST CŨ CỦA BẠN (GIỮ NGUYÊN) ==================

    @Test
    void createTask_ThrowsException_WhenUserNotInProject() {
        Project project = new Project();
        project.setMembers(new ArrayList<>());
        User user = new User();
        user.setId(99L);

        when(projectRepository.findById(any())).thenReturn(Optional.of(project));
        when(userRepository.findById(any())).thenReturn(Optional.of(user));

        TaskRequestDTO dto = new TaskRequestDTO();
        dto.setProjectId(1L);
        dto.setAssignedTo(99L);

        assertThrows(IllegalArgumentException.class, () -> taskService.createTask(dto));
    }

    @Test
    void updateStatus_ThrowsException_WhenTaskAlreadyDone() {
        Task task = new Task();
        task.setStatus(TaskStatus.DONE);
        when(taskRepository.findById(1L)).thenReturn(Optional.of(task));

        assertThrows(IllegalArgumentException.class, () -> taskService.updateTaskStatus(1L, "TODO"));
    }

    @Test
    void createTask_Success_HappyPath() {
        Project project = new Project();
        project.setId(1L);
        User user = new User();
        user.setId(2L);
        project.setMembers(java.util.List.of(user));

        when(projectRepository.findById(1L)).thenReturn(Optional.of(project));
        when(userRepository.findById(2L)).thenReturn(Optional.of(user));

        when(taskRepository.save(any(Task.class))).thenAnswer(invocation -> {
            Task savedTask = invocation.getArgument(0);
            savedTask.setId(100L);
            return savedTask;
        });

        TaskRequestDTO dto = new TaskRequestDTO();
        dto.setTitle("Học Mockito");
        dto.setStatus(TaskStatus.TODO);
        dto.setPriority(org.example.taskmanagementsystem.entity.enums.Priority.HIGH);
        dto.setProjectId(1L);
        dto.setAssignedTo(2L);
        dto.setDueDate(java.time.LocalDate.now().plusDays(5));

        var result = taskService.createTask(dto);

        assertNotNull(result);
        assertEquals("Học Mockito", result.getTitle());
        verify(taskRepository, Mockito.times(1)).save(any(Task.class));
    }

    // ================== CÁC TEST BỔ SUNG ĐỂ TĂNG COVERAGE LÊN > 90% ==================

    @Test
    void getAllTasks_Success() {
        Page<Task> page = new PageImpl<>(List.of(createValidTask()));
        when(taskRepository.findAll(any(PageRequest.class))).thenReturn(page);

        var result = taskService.getAllTasks(PageRequest.of(0, 10));
        assertEquals(1, result.getTotalElements());
    }

    @Test
    void getTasksByStatus_Success() {
        when(taskRepository.findByStatus(TaskStatus.TODO)).thenReturn(List.of(createValidTask()));
        var result = taskService.getTasksByStatus("TODO");
        assertEquals(1, result.size());
    }

    @Test
    void getTasksByStatus_ThrowsException_WhenInvalidStatus() {
        assertThrows(IllegalArgumentException.class, () -> taskService.getTasksByStatus("TRANG_THAI_BAY_BA"));
    }

    @Test
    void getTasksByUser_Success() {
        when(taskRepository.findByAssignedTo_Id(1L)).thenReturn(List.of(createValidTask()));
        var result = taskService.getTasksByUser(1L);
        assertEquals(1, result.size());
    }

    @Test
    void getTasksByProject_Success() {
        when(taskRepository.findByProject_Id(1L)).thenReturn(List.of(createValidTask()));
        var result = taskService.getTasksByProject(1L);
        assertEquals(1, result.size());
    }

    @Test
    void updateTaskStatus_Success() {
        Task task = createValidTask();
        when(taskRepository.findById(1L)).thenReturn(Optional.of(task));
        when(taskRepository.save(any(Task.class))).thenReturn(task);

        var result = taskService.updateTaskStatus(1L, "IN_PROGRESS");
        assertEquals("IN_PROGRESS", result.getStatus());
    }

    @Test
    void updateTaskStatus_ThrowsException_WhenTaskCanceled() {
        Task task = createValidTask();
        task.setStatus(TaskStatus.CANCELED);
        when(taskRepository.findById(1L)).thenReturn(Optional.of(task));

        assertThrows(IllegalArgumentException.class, () -> taskService.updateTaskStatus(1L, "DONE"));
    }

    @Test
    void deleteTask_Success() {
        when(taskRepository.existsById(1L)).thenReturn(true);
        taskService.deleteTask(1L);
        verify(taskRepository, Mockito.times(1)).deleteById(1L);
    }

    @Test
    void deleteTask_ThrowsException_WhenNotFound() {
        when(taskRepository.existsById(1L)).thenReturn(false);
        assertThrows(ResourceNotFoundException.class, () -> taskService.deleteTask(1L));
    }

    @Test
    void getTasksByUsername_Success() {
        User user = new User();
        user.setId(1L);
        when(userRepository.findByUsername("admin")).thenReturn(Optional.of(user));
        when(taskRepository.findByAssignedTo_Id(1L)).thenReturn(List.of(createValidTask()));

        var result = taskService.getTasksByUsername("admin");
        assertEquals(1, result.size());
    }
}