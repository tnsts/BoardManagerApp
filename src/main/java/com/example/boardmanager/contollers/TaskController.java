package com.example.boardmanager.contollers;

import com.example.boardmanager.domain.Column;
import com.example.boardmanager.domain.Task;
import com.example.boardmanager.repositories.TaskRepository;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("api/v1")
public class TaskController {
    private final TaskRepository taskRepository;

    public TaskController(TaskRepository taskRepository) {
        this.taskRepository = taskRepository;
    }

    @PostMapping("column/{id}/task")
    public Task createTask(@PathVariable long id, @RequestParam String title, @RequestParam String description) {
        Task task = new Task(title, description, new Column());
        task.getColumn().setId(id);
        return taskRepository.save(task);
    }

    @PutMapping("task/{id}")
    public Task updateTask(
            @PathVariable long id,
            @RequestParam(required = false) String title,
            @RequestParam(required = false) String description
    ) {
        Task task = taskRepository.findById(id);
        if (title != null)
            task.setTitle(title);
        if (description != null)
            task.setDescription(description);
        return taskRepository.save(task);
    }

    @DeleteMapping("task/{id}")
    public Task deleteTask(@PathVariable long id) {
        Task column = taskRepository.findById(id);
        taskRepository.delete(column);
        return column;
    }
}