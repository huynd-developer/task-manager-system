package org.example.taskmanagementsystem.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.Data;

import java.util.List;

@Data
public class ProjectRequestDTO {
    @NotBlank(message = "Tên dự án không được để trống")
    private String name;

    @NotBlank(message = "Trạng thái không được để trống")
    private String status;

    private String description;
    private List<Long> memberIds;
}
