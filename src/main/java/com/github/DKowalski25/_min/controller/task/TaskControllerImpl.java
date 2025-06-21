package com.github.DKowalski25._min.controller.task;

import com.github.DKowalski25._min.dto.task.TaskRequestDTO;
import com.github.DKowalski25._min.dto.task.TaskResponseDTO;
import com.github.DKowalski25._min.dto.task.TaskUpdateDTO;
import com.github.DKowalski25._min.models.CustomUserDetails;
import com.github.DKowalski25._min.service.task.TaskService;

import lombok.RequiredArgsConstructor;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/tasks")
public class TaskControllerImpl implements TaskController {
    private final TaskService taskService;

    @Override
    public ResponseEntity<TaskResponseDTO> createTask(
            @AuthenticationPrincipal CustomUserDetails userDetails,
            @RequestBody TaskRequestDTO taskRequestDTO) {
        TaskResponseDTO createdTask = taskService.createTask(taskRequestDTO, userDetails.getId());
        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(createdTask);
    }

    @Override
    public ResponseEntity<TaskResponseDTO> getTaskById(@PathVariable int id) {
        return ResponseEntity.ok(taskService.getTaskById(id));
    }

    @Override
    public ResponseEntity<List<TaskResponseDTO>> getAllTasks(
            @AuthenticationPrincipal CustomUserDetails userDetails
    ) {
        return ResponseEntity.ok(taskService.getAllTasks(userDetails.getId()));
    }

    @Override
    public ResponseEntity<TaskResponseDTO> updateTask(
            @AuthenticationPrincipal CustomUserDetails userDetails,
            @PathVariable int id,
            @RequestBody TaskUpdateDTO taskRequestDTO) {
        return ResponseEntity.ok(taskService.updateTask(id, taskRequestDTO, userDetails.getId()));
    }

    @Override
    public ResponseEntity<Void> deleteTask(
            @AuthenticationPrincipal CustomUserDetails userDetails,
            @PathVariable int id) {
        taskService.deleteTask(id, userDetails.getId());
        return ResponseEntity.noContent().build();
    }
}
