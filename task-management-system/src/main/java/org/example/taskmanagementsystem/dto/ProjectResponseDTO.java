package org.example.taskmanagementsystem.dto;

import lombok.Data;

import java.time.LocalDate;

@Data
public class ProjectResponseDTO {
    private Long id;
    private String name;
    private String status;
    private LocalDate dueDate;
}
