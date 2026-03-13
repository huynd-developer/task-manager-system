package org.example.taskmanagementsystem.entity;

import jakarta.persistence.*;
import org.example.taskmanagementsystem.entity.enums.ProjectStatus;
import java.time.LocalDate;
import java.util.List;
import lombok.Getter;
import lombok.Setter;

@Entity
@Table(name = "projects")
@Getter
@Setter
public class Project extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String name;

    private String description;

    @Enumerated(EnumType.STRING)
    private ProjectStatus status;

    private LocalDate startDate;
    private LocalDate endDate;



    @ManyToMany
    @JoinTable(
            name = "project_members",
            joinColumns = @JoinColumn(name = "project_id"),
            inverseJoinColumns = @JoinColumn(name = "user_id")
    )
    private List<User> members;
}