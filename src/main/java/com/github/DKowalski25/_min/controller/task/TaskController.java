package com.github.DKowalski25._min.controller.task;

import com.github.DKowalski25._min.dto.task.TaskRequestDTO;
import com.github.DKowalski25._min.dto.task.TaskResponseDTO;
import com.github.DKowalski25._min.dto.task.TaskUpdateDTO;

import org.springframework.http.ResponseEntity;

import org.springframework.web.bind.annotation.*;

import java.util.List;

@RequestMapping("/api/tasks")
public interface TaskController {

    @PostMapping
    ResponseEntity<TaskResponseDTO> createTask(@RequestBody TaskRequestDTO taskRequestDTO);

    @GetMapping("/{id}")
    ResponseEntity<TaskResponseDTO> getTaskById(@PathVariable int taskRequestDTO);

    @GetMapping
    ResponseEntity<List<TaskResponseDTO>> getAllTasks();

    @PatchMapping("/{id}")
    ResponseEntity<TaskResponseDTO> updateTask(@PathVariable int id, @RequestBody TaskUpdateDTO taskRequestDTO);

    @DeleteMapping("/{id}")
    ResponseEntity<Void> deleteTask(@PathVariable int id);
}