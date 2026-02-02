package org.example.taskmanagementsystem.service;

import org.example.taskmanagementsystem.dto.TaskRequestDTO;
import org.example.taskmanagementsystem.dto.TaskResponseDTO;
import org.example.taskmanagementsystem.entity.Project;
import org.example.taskmanagementsystem.entity.Task;
import org.example.taskmanagementsystem.entity.User;
import org.example.taskmanagementsystem.entity.enums.TaskStatus;
import org.example.taskmanagementsystem.exception.ResourceNotFoundException;
import org.example.taskmanagementsystem.repository.ProjectRepository;
import org.example.taskmanagementsystem.repository.TaskRepository;
import org.example.taskmanagementsystem.repository.UserRepository;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class TaskService {

    private final TaskRepository taskRepository;
    private final ProjectRepository projectRepository;
    private final UserRepository userRepository;

    public TaskService(
            TaskRepository taskRepository,
            ProjectRepository projectRepository,
            UserRepository userRepository
    ) {
        this.taskRepository = taskRepository;
        this.projectRepository = projectRepository;
        this.userRepository = userRepository;
    }

    private TaskResponseDTO mapToDTO(Task task) {
        TaskResponseDTO dto = new TaskResponseDTO();

        dto.setId(task.getId());
        dto.setTitle(task.getTitle());
        dto.setStatus(task.getStatus().name());
        dto.setPriority(task.getPriority().name());
        dto.setCreatedAt(task.getCreatedAt());


        if (task.getProject() != null) {
            dto.setProjectName(task.getProject().getName());
        }


        if (task.getAssignedTo() != null) {
            dto.setAssignedUser(task.getAssignedTo().getFullName());
        }

        return dto;
    }


    public List<TaskResponseDTO> getAllTasks() {
        return taskRepository.findAll()
                .stream()
                .map(this::mapToDTO)
                .collect(Collectors.toList());
    }


    public List<TaskResponseDTO> getTasksByStatus(String status) {
        TaskStatus taskStatus = TaskStatus.valueOf(status.toUpperCase());
        return taskRepository.findByStatus(taskStatus)
                .stream()
                .map(this::mapToDTO)
                .collect(Collectors.toList());
    }


    public List<TaskResponseDTO> getTasksByUser(Long userId) {
        return taskRepository.findByAssignedTo_Id(userId)
                .stream()
                .map(this::mapToDTO)
                .collect(Collectors.toList());
    }


    public List<TaskResponseDTO> getTasksByProject(Long projectId) {
        return taskRepository.findByProject_Id(projectId)
                .stream()
                .map(this::mapToDTO)
                .collect(Collectors.toList());
    }


    public TaskResponseDTO createTask(TaskRequestDTO dto) {

        Project project = projectRepository.findById(dto.getProjectId())
                .orElseThrow(() ->
                        new ResourceNotFoundException("Project not found"));

        User user = userRepository.findById(dto.getAssignedTo())
                .orElseThrow(() ->
                        new ResourceNotFoundException("User not found"));

        Task task = new Task();
        task.setTitle(dto.getTitle());
        task.setDescription(dto.getDescription());
        task.setStatus(dto.getStatus());
        task.setPriority(dto.getPriority());
        task.setProject(project);
        task.setAssignedTo(user);
        task.setCreatedAt(LocalDateTime.now());
        task.setUpdatedAt(LocalDateTime.now());

        Task saved = taskRepository.save(task);
        return mapToDTO(saved);
    }


    public TaskResponseDTO updateTaskStatus(Long taskId, String status) {

        Task task = taskRepository.findById(taskId)
                .orElseThrow(() ->
                        new ResourceNotFoundException("Task not found"));

        task.setStatus(TaskStatus.valueOf(status.toUpperCase()));
        task.setUpdatedAt(LocalDateTime.now());

        return mapToDTO(taskRepository.save(task));
    }


    public void deleteTask(Long id) {
        Task task = taskRepository.findById(id)
                .orElseThrow(() ->
                        new ResourceNotFoundException("Task not found"));
        taskRepository.delete(task);
    }
}
