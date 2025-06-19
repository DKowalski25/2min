package com.github.DKowalski25._min.controller.task;

import com.github.DKowalski25._min.dto.task.TaskRequestDTO;
import com.github.DKowalski25._min.dto.task.TaskResponseDTO;
import com.github.DKowalski25._min.dto.task.TaskUpdateDTO;
import com.github.DKowalski25._min.service.task.TaskService;

import lombok.RequiredArgsConstructor;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/tasks")
public class TaskControllerImpl implements TaskController {
    private final TaskService taskService;

    @Override
    public ResponseEntity<TaskResponseDTO> createTask(TaskRequestDTO taskRequestDTO) {
        TaskResponseDTO createdTask = taskService.createTask(taskRequestDTO);
        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(createdTask);
    }

    @Override
    public ResponseEntity<TaskResponseDTO> getTaskById(int id) {
        return ResponseEntity.ok(taskService.getTaskById(id));
    }

    @Override
    public ResponseEntity<List<TaskResponseDTO>> getAllTasks() {
        return ResponseEntity.ok(taskService.getAllTasks());
    }

    @Override
    public ResponseEntity<TaskResponseDTO> updateTask(int id, TaskUpdateDTO taskRequestDTO) {
        return ResponseEntity.ok(taskService.updateTask(id, taskRequestDTO));
    }

    @Override
    public ResponseEntity<Void> deleteTask(int id) {
        taskService.deleteTask(id);
        return ResponseEntity.noContent().build();
    }
}
