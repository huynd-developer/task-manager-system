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
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

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

        dto.setDueDate(task.getDueDate());

        if (task.getProject() != null) {
            dto.setProjectName(task.getProject().getName());
        }

        if (task.getAssignedTo() != null) {
            dto.setAssignedUser(task.getAssignedTo().getFullName());
        }
        return dto;
    }


    public Page<TaskResponseDTO> getAllTasks(Pageable pageable) {
        return taskRepository.findAll(pageable)
                .map(this::mapToDTO);
    }

    public List<TaskResponseDTO> getTasksByStatus(String status) {
        try {
            TaskStatus taskStatus = TaskStatus.valueOf(status.toUpperCase());
            return taskRepository.findByStatus(taskStatus)
                    .stream()
                    .map(this::mapToDTO)
                    .collect(Collectors.toList());
        } catch (IllegalArgumentException e) {
            throw new IllegalArgumentException("Trạng thái '" + status + "' không hợp lệ!");
        }
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

    @Transactional
    public TaskResponseDTO createTask(TaskRequestDTO dto) {
        Project project = projectRepository.findById(dto.getProjectId())
                .orElseThrow(() -> new ResourceNotFoundException("Project not found"));

        User user = userRepository.findById(dto.getAssignedTo())
                .orElseThrow(() -> new ResourceNotFoundException("User not found"));

        boolean isMember = project.getMembers().contains(user);
        if (!isMember) {
            throw new IllegalArgumentException("User không thuộc dự án này, không thể giao việc!");
        }

        Task task = new Task();
        task.setTitle(dto.getTitle());
        task.setDescription(dto.getDescription());
        task.setStatus(dto.getStatus());
        task.setPriority(dto.getPriority());
        task.setProject(project);
        task.setAssignedTo(user);

        task.setDueDate(dto.getDueDate());


        Task saved = taskRepository.save(task);
        return mapToDTO(saved);
    }

    @Transactional
    public TaskResponseDTO updateTaskStatus(Long taskId, String status) {
        Task task = taskRepository.findById(taskId)
                .orElseThrow(() -> new ResourceNotFoundException("Task not found"));

        if (task.getStatus() == TaskStatus.DONE) {
            throw new IllegalArgumentException("Không thể cập nhật Task đã hoàn thành!");
        }

        if (task.getStatus() == TaskStatus.CANCELED) {
            throw new IllegalArgumentException("Không thể cập nhật Task đã bị hủy!");
        }

        try {
            task.setStatus(TaskStatus.valueOf(status.toUpperCase()));
        } catch (IllegalArgumentException e) {
            throw new IllegalArgumentException("Trạng thái '" + status + "' không hợp lệ!");
        }


        return mapToDTO(taskRepository.save(task));
    }

    @Transactional
    public void deleteTask(Long id) {
        if (!taskRepository.existsById(id)) {
            throw new ResourceNotFoundException("Task not found");
        }
        taskRepository.deleteById(id);
    }
    public List<TaskResponseDTO> getTasksByUsername(String username) {
        User user = userRepository.findByUsername(username)
                .orElseThrow(() -> new ResourceNotFoundException("Không tìm thấy User"));

        return taskRepository.findByAssignedTo_Id(user.getId())
                .stream()
                .map(this::mapToDTO)
                .collect(Collectors.toList());
    }
}