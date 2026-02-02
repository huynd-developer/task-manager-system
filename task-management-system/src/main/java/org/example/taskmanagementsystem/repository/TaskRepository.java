package org.example.taskmanagementsystem.repository;

import org.example.taskmanagementsystem.entity.Task;
import org.example.taskmanagementsystem.entity.enums.TaskStatus;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface TaskRepository extends JpaRepository<Task, Long> {

    List<Task> findByStatus(TaskStatus status);

    List<Task> findByAssignedTo_Id(Long userId);

    List<Task> findByProject_Id(Long projectId);
}
