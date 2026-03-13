package org.example.taskmanagementsystem.dto;

import lombok.Data;
import java.time.LocalDateTime;
import java.time.LocalDate;
@Data
public class TaskResponseDTO {
    private Long id;
    private String title;
    private String status;
    private String priority;
    private String projectName;
    private String assignedUser;
    private LocalDateTime createdAt;
    private LocalDate dueDate;
}
