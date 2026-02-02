package org.example.taskmanagementsystem.dto;

import lombok.Data;
import java.time.LocalDateTime;

@Data
public class TaskResponseDTO {
    private Long id;
    private String title;
    private String status;
    private String priority;
    private String projectName;
    private String assignedUser;
    private LocalDateTime createdAt;
}
