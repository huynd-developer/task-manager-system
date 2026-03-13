package org.example.taskmanagementsystem.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;
import org.example.taskmanagementsystem.entity.enums.Priority;
import org.example.taskmanagementsystem.entity.enums.TaskStatus;
import jakarta.validation.constraints.FutureOrPresent;
import java.time.LocalDate;
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

    @NotNull(message = "Deadline không được để trống")
    @FutureOrPresent(message = "Deadline phải lớn hơn hoặc bằng ngày hiện tại")
    private LocalDate dueDate;
}
