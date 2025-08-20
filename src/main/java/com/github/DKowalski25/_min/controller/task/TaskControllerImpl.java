package com.github.DKowalski25._min.controller.task;

import com.github.DKowalski25._min.dto.task.TaskRequestDTO;
import com.github.DKowalski25._min.dto.task.TaskResponseDTO;
import com.github.DKowalski25._min.dto.task.TaskUpdateDTO;
import com.github.DKowalski25._min.models.config.CustomUserDetails;
import com.github.DKowalski25._min.service.task.TaskService;

import lombok.RequiredArgsConstructor;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/tasks")
public class TaskControllerImpl implements TaskController {
    private final TaskService taskService;

    @Override
    @PostMapping
    public ResponseEntity<TaskResponseDTO> createTask(
            @AuthenticationPrincipal CustomUserDetails userDetails,
            @RequestBody TaskRequestDTO taskRequestDTO) {
        TaskResponseDTO createdTask = taskService.createTask(taskRequestDTO, userDetails.getId());
        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(createdTask);
    }

    @Override
    @GetMapping("/{id}")
    public ResponseEntity<TaskResponseDTO> getTaskById(
            @AuthenticationPrincipal CustomUserDetails userDetails,
            @PathVariable UUID id) {
        return ResponseEntity.ok(taskService.getTaskById(id, userDetails.getId()));
    }

    @Override
    @GetMapping
    public ResponseEntity<List<TaskResponseDTO>> getAllTasks(
            @AuthenticationPrincipal CustomUserDetails userDetails
    ) {
        return ResponseEntity.ok(taskService.getAllTasks(userDetails.getId()));
    }

    @Override
    @PatchMapping("/{id}")
    public ResponseEntity<TaskResponseDTO> updateTask(
            @AuthenticationPrincipal CustomUserDetails userDetails,
            @PathVariable UUID id,
            @RequestBody TaskUpdateDTO taskRequestDTO) {
        return ResponseEntity.ok(taskService.updateTask(id, taskRequestDTO, userDetails.getId()));
    }

    @Override
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteTask(
            @AuthenticationPrincipal CustomUserDetails userDetails,
            @PathVariable UUID id) {
        taskService.deleteTask(id, userDetails.getId());
        return ResponseEntity.noContent().build();
    }
}
