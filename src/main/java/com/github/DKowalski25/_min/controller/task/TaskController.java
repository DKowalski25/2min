package com.github.DKowalski25._min.controller.task;

import com.github.DKowalski25._min.dto.task.TaskRequestDTO;
import com.github.DKowalski25._min.dto.task.TaskResponseDTO;
import com.github.DKowalski25._min.dto.task.TaskUpdateDTO;

import com.github.DKowalski25._min.repository.task.TaskRepository;
import org.springframework.http.ResponseEntity;

import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * REST controller for managing task operations.
 *
 * <p>Provides endpoints for task CRUD operations.
 * All methods return {@link ResponseEntity} with appropriate HTTP status codes.</p>
 *
 * <p><strong>Usage example:</strong>
 * <pre>{@code
 * // Create task
 * TaskRequestDTO newTask = new TaskRequestDTO(...);
 * ResponseEntity<TaskResponseDTO> response = taskController.createTask(newTask);
 * }</pre></p>
 * @see TaskController
 * @see TaskRepository
 * @see TaskRequestDTO
 * @see TaskResponseDTO
 * @see TaskUpdateDTO
 */
public interface TaskController {

    /**
     * Creates a new task.
     *
     * @param taskRequestDTO the task creation data (must not be {@code null})
     * @return {@link ResponseEntity} with created task data and HTTP status 201 (Created)
     */
    @PostMapping
    ResponseEntity<TaskResponseDTO> createTask(@RequestBody TaskRequestDTO taskRequestDTO);

    /**
     * Retrieves a task by ID.
     *
     * @param id the task identifier
     * @return {@link ResponseEntity} with task data and HTTP status 200 (OK),
     *         or status 404 (Not Found) if task doesn't exist
     */
    @GetMapping("/{id}")
    ResponseEntity<TaskResponseDTO> getTaskById(@PathVariable int id);

    /**
     * Retrieves all tasks.
     *
     * @return {@link ResponseEntity} with list of all tasks and HTTP status 200 (OK),
     *         or empty list if no tasks exist
     */
    @GetMapping
    ResponseEntity<List<TaskResponseDTO>> getAllTasks();

    /**
     * Updates an existing task.
     *
     * @param id the task identifier to update
     * @param taskRequestDTO the task update data (must not be {@code null})
     * @return {@link ResponseEntity} with updated task data and HTTP status 200 (OK),
     *         or status 404 (Not Found) if task doesn't exist
     */
    @PatchMapping("/{id}")
    ResponseEntity<TaskResponseDTO> updateTask(@PathVariable int id, @RequestBody TaskUpdateDTO taskRequestDTO);

    /**
     * Deletes a task by ID.
     *
     * @param id the task identifier to delete
     * @return {@link ResponseEntity} with HTTP status 204 (No Content) if successful,
     *         or status 404 (Not Found) if task doesn't exist
     */
    @DeleteMapping("/{id}")
    ResponseEntity<Void> deleteTask(@PathVariable int id);
}