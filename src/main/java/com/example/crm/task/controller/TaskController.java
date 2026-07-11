package com.example.crm.task.controller;

import com.example.crm.task.dto.TaskRequest;
import com.example.crm.task.entity.Task;
import com.example.crm.task.entity.TaskStatus;
import com.example.crm.task.service.TaskService;
import com.example.crm.task.dto.TaskResponse;

import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/tasks")
@CrossOrigin(origins = "*")
public class TaskController {

    private final TaskService taskService;

    public TaskController(TaskService taskService) {
        this.taskService = taskService;
    }

    @PostMapping
    public TaskResponse createTask(
            @RequestBody TaskRequest request) {

        return taskService.createTask(request);
    }

    @GetMapping
    public List<TaskResponse> getAllTasks() {

        return taskService.getAllTasks();
    }

    @GetMapping("/pending")
    public List<TaskResponse> getPendingTasks() {

        return taskService.getPendingTasks();
    }

    @PutMapping("/{id}/status")
    public TaskResponse updateStatus(
            @PathVariable Long id,
            @RequestParam String status) {

        return taskService.updateStatus(
                id,
                TaskStatus.valueOf(status));
    }
}