package org.example.todoapp.Controller;

import jakarta.validation.Valid;
import org.example.todoapp.Entity.Todo;
import org.example.todoapp.Service.TodoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.List;

@Controller
@RequestMapping("/todos")
public class TodoController {
    @Autowired
    private TodoService todoService;

    @GetMapping
    public String listTodos(
            @RequestParam(required = false) String filter,
            @RequestParam(required = false) String search,
            Model model) {

        List<Todo> todos;

        if (search != null && !search.trim().isEmpty()) {
            todos = todoService.searchTodos(search);
            model.addAttribute("searchTerm", search);
        } else if ("completed".equals(filter)) {
            todos = todoService.getCompletedTodos();
        } else if ("pending".equals(filter)) {
            todos = todoService.getPendingTodos();
        } else if ("overdue".equals(filter)) {
            todos = todoService.getOverdueTodos();
        } else {
            todos = todoService.getAllTodos();
        }

        model.addAttribute("todos", todos);
        model.addAttribute("filter", filter);
        model.addAttribute("priorities", Todo.Priority.values());
        model.addAttribute("pendingCount", todoService.getPendingTodos().size());
        model.addAttribute("completedCount", todoService.getCompletedTodos().size());
        model.addAttribute("overdueCount", todoService.getOverdueTodos().size());

        return "todos/list";
    }

    @GetMapping("/create")
    public String showCreateForm(Model model) {
        model.addAttribute("todo", new Todo());
        model.addAttribute("priorities", Todo.Priority.values());
        return "todos/form";
    }

    @PostMapping("/create")
    public String createTodo(@Valid @ModelAttribute Todo todo,
                             BindingResult result,
                             Model model,
                             RedirectAttributes redirectAttributes) {

        if (result.hasErrors()) {
            model.addAttribute("priorities", Todo.Priority.values());
            return "todos/form";
        }

        todoService.saveTodo(todo);
        redirectAttributes.addFlashAttribute("successMessage", "Todo created successfully!");
        return "redirect:/todos";
    }

    @GetMapping("/edit/{id}")
    public String showEditForm(@PathVariable Long id, Model model) {
        Todo todo = todoService.getTodoById(id)
                .orElseThrow(() -> new IllegalArgumentException("Invalid todo id: " + id));

        model.addAttribute("todo", todo);
        model.addAttribute("priorities", Todo.Priority.values());
        return "todos/form";
    }

    @PostMapping("/edit/{id}")
    public String updateTodo(@PathVariable Long id,
                             @Valid @ModelAttribute Todo todo,
                             BindingResult result,
                             Model model,
                             RedirectAttributes redirectAttributes) {

        if (result.hasErrors()) {
            model.addAttribute("priorities", Todo.Priority.values());
            return "todos/form";
        }

        todo.setId(id);
        todoService.saveTodo(todo);
        redirectAttributes.addFlashAttribute("successMessage", "Todo updated successfully!");
        return "redirect:/todos";
    }

    @GetMapping("/toggle/{id}")
    public String toggleTodoStatus(@PathVariable Long id, RedirectAttributes redirectAttributes) {
        Todo todo = todoService.toggleTodoStatus(id);
        if (todo != null) {
            String message = todo.isCompleted() ? "Todo marked as completed!" : "Todo marked as pending!";
            redirectAttributes.addFlashAttribute("successMessage", message);
        }
        return "redirect:/todos";
    }

    @GetMapping("/delete/{id}")
    public String deleteTodo(@PathVariable Long id, RedirectAttributes redirectAttributes) {
        todoService.deleteTodo(id);
        redirectAttributes.addFlashAttribute("successMessage", "Todo deleted successfully!");
        return "redirect:/todos";
    }
}
