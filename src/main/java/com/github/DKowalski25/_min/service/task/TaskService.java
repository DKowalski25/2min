package com.github.DKowalski25._min.service.task;

import com.github.DKowalski25._min.controller.task.TaskController;
import com.github.DKowalski25._min.dto.task.TaskRequestDTO;
import com.github.DKowalski25._min.dto.task.TaskResponseDTO;
import com.github.DKowalski25._min.dto.task.TaskUpdateDTO;
import com.github.DKowalski25._min.exceptions.EntityNotFoundException;
import com.github.DKowalski25._min.repository.task.TaskRepository;

import java.util.List;

/**
 * Service interface for task management operations.
 *
 * <p><strong>Note:</strong> This service handles business logic for task operations.
 * All methods throw appropriate exceptions for error cases that should be handled by the caller.</p>
 *
 * <p><strong>Usage example:</strong>
 * <pre>{@code
 * // Create new task
 * TaskResponseDTO newTask = taskService.createTask(taskRequest);
 *
 * // Get task by ID
 * TaskResponseDTO task = taskService.getTaskById(1);
 * }</pre></p>
 *
 * @see TaskController
 * @see TaskRepository
 * @see TaskRequestDTO
 * @see TaskResponseDTO
 * @see TaskUpdateDTO
 */
public interface TaskService {

    /**
     * Creates a new task based on the provided request data.
     *
     * @param taskRequestDTO the task creation data (must not be {@code null})
     * @param userId the ID of the user creating the task
     * @return the created task response DTO
     * @throws IllegalArgumentException if the request data is invalid
     */
    TaskResponseDTO createTask(TaskRequestDTO taskRequestDTO, int userId);

    /**
     * Retrieves a task by its unique identifier.
     *
     * @param id the task identifier to search for
     * @return the task response DTO
     * @throws EntityNotFoundException if no task exists with the given ID
     */
    TaskResponseDTO getTaskById(int id);

    /**
     * Retrieves all available tasks.
     *
     * @return a list of task response DTOs (possibly empty)
     */
    List<TaskResponseDTO> getAllTasks();

    /**
     * Updates an existing task with the provided data.
     *
     * @param id the identifier of the task to update
     * @param taskUpdateDTO the task update data (must not be {@code null})
     * @param userId the ID of the user
     * @return the updated task response DTO
     * @throws EntityNotFoundException if no task exists with the given ID
     * @throws IllegalArgumentException if the update data is invalid
     */
    TaskResponseDTO updateTask(int id, TaskUpdateDTO taskUpdateDTO, int userId);

    /**
     * Deletes a task by its identifier.
     *
     * @param id the identifier of the task to delete
     * @param userId the ID of the user
     * @throws EntityNotFoundException if no task exists with the given ID
     */
    void deleteTask(int id, int userId);
}
