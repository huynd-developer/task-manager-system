package org.example.taskmanagementsystem.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;
import org.example.taskmanagementsystem.entity.enums.Priority;
import org.example.taskmanagementsystem.entity.enums.TaskStatus;

@Data
public class TaskRequestDTO {

    @NotBlank
    private String title;

    private String description;

    @NotNull
    private TaskStatus status;

    @NotNull
    private Priority priority;

    @NotNull
    private Long projectId;

    @NotNull
    private Long assignedTo;
}
