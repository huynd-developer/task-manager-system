package org.example.todoapp.Service;

import org.example.todoapp.Entity.Todo;
import org.example.todoapp.Repository.TodoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class TodoService {

    @Autowired
    private TodoRepository todoRepository;

    public List<Todo> getAllTodos() {
        return todoRepository.findAllByOrderByCreatedAtDesc();
    }

    public List<Todo> getCompletedTodos() {
        return todoRepository.findByCompletedOrderByCreatedAtDesc(true);
    }

    public List<Todo> getPendingTodos() {
        return todoRepository.findByCompletedOrderByCreatedAtDesc(false);
    }

    public List<Todo> getTodosByPriority(Todo.Priority priority) {
        return todoRepository.findByPriorityOrderByCreatedAtDesc(priority);
    }

    public List<Todo> searchTodos(String keyword) {
        return todoRepository.findByTitleContainingIgnoreCaseOrderByCreatedAtDesc(keyword);
    }

    public List<Todo> getOverdueTodos() {
        return todoRepository.findOverdueTodos();
    }

    public Optional<Todo> getTodoById(Long id) {
        return todoRepository.findById(id);
    }

    public Todo saveTodo(Todo todo) {
        return todoRepository.save(todo);
    }

    public void deleteTodo(Long id) {
        todoRepository.deleteById(id);
    }

    public Todo toggleTodoStatus(Long id) {
        Optional<Todo> todoOpt = todoRepository.findById(id);
        if (todoOpt.isPresent()) {
            Todo todo = todoOpt.get();
            todo.setCompleted(!todo.isCompleted());
            return todoRepository.save(todo);
        }
        return null;
    }
}