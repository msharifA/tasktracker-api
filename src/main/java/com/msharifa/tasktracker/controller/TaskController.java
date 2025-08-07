package com.msharifa.tasktracker.controller;

import com.msharifa.tasktracker.model.Task;
import com.msharifa.tasktracker.repository.TaskRepository;
import com.msharifa.tasktracker.util.ExcelExporter;

import jakarta.validation.Valid;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;

import org.springframework.web.bind.annotation.*;

import java.io.ByteArrayInputStream;
import java.io.IOException;
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

    @GetMapping("/export")
    public ResponseEntity<byte[]> exportTasksToExcel() throws IOException {
        List<Task> tasks = repo.findAll();
        ByteArrayInputStream in = ExcelExporter.tasksToExcel(tasks);

        HttpHeaders headers = new HttpHeaders();
        headers.add("Content-Disposition", "attachment; filename=tasks.xlsx");

        return ResponseEntity
                .ok()
                .headers(headers)
                .contentType(MediaType.APPLICATION_OCTET_STREAM)
                .body(in.readAllBytes());
    }
}
