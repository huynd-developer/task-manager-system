package org.example.todoapp.Repository;

import org.example.todoapp.Entity.Todo;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface TodoRepository extends JpaRepository<Todo,Long> {
    List<Todo> findByCompletedOrderByCreatedAtDesc(boolean completed);

    List<Todo> findAllByOrderByCreatedAtDesc();

    List<Todo> findByPriorityOrderByCreatedAtDesc(Todo.Priority priority);

    @Query("SELECT t FROM Todo t WHERE t.completed = false AND t.dueDate < CURRENT_TIMESTAMP ORDER BY t.dueDate ASC")
    List<Todo> findOverdueTodos();

    List<Todo> findByTitleContainingIgnoreCaseOrderByCreatedAtDesc(String title);
}