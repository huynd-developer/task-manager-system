package org.example.taskmanagementsystem.service;

import org.example.taskmanagementsystem.dto.ProjectResponseDTO;
import org.example.taskmanagementsystem.entity.Project;
import org.example.taskmanagementsystem.repository.ProjectRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ProjectService {
    private final ProjectRepository projectRepository;

    public ProjectService(ProjectRepository projectRepository) {
        this.projectRepository = projectRepository;
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


    public Project createProject(Project project) {
        return projectRepository.save(project);
    }
}
