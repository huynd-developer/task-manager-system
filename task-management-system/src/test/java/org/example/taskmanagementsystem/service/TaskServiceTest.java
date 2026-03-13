package org.example.taskmanagementsystem.service;

import org.example.taskmanagementsystem.dto.TaskRequestDTO;
import org.example.taskmanagementsystem.entity.Project;
import org.example.taskmanagementsystem.entity.Task;
import org.example.taskmanagementsystem.entity.User;
import org.example.taskmanagementsystem.entity.enums.TaskStatus;
import org.example.taskmanagementsystem.repository.ProjectRepository;
import org.example.taskmanagementsystem.repository.TaskRepository;
import org.example.taskmanagementsystem.repository.UserRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.ArrayList;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class TaskServiceTest {
    @Mock    private TaskRepository taskRepository;
    @Mock private ProjectRepository projectRepository;
    @Mock private UserRepository userRepository;
    @InjectMocks private TaskService taskService;

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

        org.junit.jupiter.api.Assertions.assertNotNull(result);
        org.junit.jupiter.api.Assertions.assertEquals("Học Mockito", result.getTitle());

        org.mockito.Mockito.verify(taskRepository, org.mockito.Mockito.times(1)).save(any(Task.class));
    }
}