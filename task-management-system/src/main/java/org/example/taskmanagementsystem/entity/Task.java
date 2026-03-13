package org.example.taskmanagementsystem.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import org.example.taskmanagementsystem.entity.enums.Priority;
import org.example.taskmanagementsystem.entity.enums.TaskStatus;

import java.time.LocalDate;

@Entity
@Table(name = "tasks")
@Getter
@Setter
public class Task extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String title;

    private String description;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private TaskStatus status;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private Priority priority;

    @ManyToOne
    @JoinColumn(name = "project_id", nullable = false)
    private Project project;

    @ManyToOne
    @JoinColumn(name = "assigned_to", nullable = false)
    private User assignedTo;

    @Column(name = "due_date", nullable = false)
    private LocalDate dueDate;
}