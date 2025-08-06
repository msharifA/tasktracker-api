package com.msharifa.tasktracker.controller;

import com.msharifa.tasktracker.model.Task;
import com.msharifa.tasktracker.repository.TaskRepository;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/tasks")
public class TaskController {

    private final TaskRepository repo;

    public TaskController(TaskRepository repo) {
        this.repo = repo;
    }

    @PostMapping
    public ResponseEntity<Task> createTask(@Valid @RequestBody Task task) {
        Task saved = repo.save(task);
        return ResponseEntity.ok(saved);
    }

    @GetMapping
    public List<Task> getAllTasks() {
        return repo.findAll();
    }
}
