package org.example.taskmanagementsystem.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import org.example.taskmanagementsystem.entity.enums.UserRole;
import java.util.List;

@Entity
@Table(name = "users")
@Getter
@Setter
public class User extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String username;
    private String password;
    private String fullName;

    @Enumerated(EnumType.STRING)
    private UserRole role;

    @ManyToMany(mappedBy = "members")
    private List<Project> projects;


}