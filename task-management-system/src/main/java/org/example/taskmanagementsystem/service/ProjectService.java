package org.example.taskmanagementsystem.service;

import org.example.taskmanagementsystem.dto.ProjectRequestDTO;
import org.example.taskmanagementsystem.dto.ProjectResponseDTO;
import org.example.taskmanagementsystem.entity.Project;
import org.example.taskmanagementsystem.entity.User;
import org.example.taskmanagementsystem.entity.enums.ProjectStatus;
import org.example.taskmanagementsystem.repository.ProjectRepository;
import org.example.taskmanagementsystem.repository.UserRepository;
import org.springframework.stereotype.Service;
import org.example.taskmanagementsystem.exception.ResourceNotFoundException;
import java.util.List;

@Service
public class ProjectService {
    private final ProjectRepository projectRepository;
    private final UserRepository userRepository;

    public ProjectService(ProjectRepository projectRepository, UserRepository userRepository) {
        this.projectRepository = projectRepository;
        this.userRepository = userRepository;
    }

    public List<ProjectResponseDTO> getAllProjects() {
        return projectRepository.findAll()
                .stream()
                .map(project -> {
                    ProjectResponseDTO dto = new ProjectResponseDTO();
                    dto.setId(project.getId());
                    dto.setName(project.getName());
                    dto.setStatus(project.getStatus().name());
                    return dto;
                })
                .toList();
    }


    public ProjectResponseDTO createProject(ProjectRequestDTO dto) {
        Project project = new Project();
        project.setName(dto.getName());
        project.setDescription(dto.getDescription());

        // 1. Xử lý Enum an toàn để tránh lỗi 500
        try {
            project.setStatus(ProjectStatus.valueOf(dto.getStatus().toUpperCase()));
        } catch (Exception e) {
            throw new IllegalArgumentException("Trạng thái dự án '" + dto.getStatus() + "' không hợp lệ!");
        }

        if (dto.getMemberIds() != null) {
            List<User> members = userRepository.findAllById(dto.getMemberIds());
            project.setMembers(members);
        }

        // 2. KHÔNG setCreatedAt/UpdatedAt ở đây. JPA Auditing sẽ tự điền.
        Project saved = projectRepository.save(project);
        ProjectResponseDTO response = new ProjectResponseDTO();
        response.setId(saved.getId());
        response.setName(saved.getName());
        response.setStatus(saved.getStatus().name());
        return response;
    }
    public void deleteProject(Long id) {
        if (!projectRepository.existsById(id)) {
            throw new ResourceNotFoundException("Không tìm thấy dự án với ID: " + id);
        }
        projectRepository.deleteById(id);
    }
}
