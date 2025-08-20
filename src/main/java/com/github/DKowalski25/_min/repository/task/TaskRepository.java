package com.github.DKowalski25._min.repository.task;

import com.github.DKowalski25._min.controller.task.TaskController;
import com.github.DKowalski25._min.models.Task;

import com.github.DKowalski25._min.service.task.TaskService;
import org.springframework.data.jpa.repository.JpaRepository;

import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

/**
 * Spring Data JPA repository for {@link Task} entities management.
 *
 * <p><strong>Note:</strong> This interface relies on Spring Data JPA capabilities.
 * All query methods return {@link Optional} or primitive results where appropriate.
 * Business exception handling should be performed at the service layer.</p>
 *
 * <p><strong>Usage example:</strong>
 * <pre>{@code
 * // In service layer:
 * Task task = taskRepository.findById(taskId)
 *     .orElseThrow(() -> new EntityNotFoundException("Task not found"));
 * }</pre></p>
 *
 * @see TaskService
 * @see TaskController
 */
@Repository
public interface TaskRepository extends JpaRepository<Task, UUID> {
    List<Task> findByUserId(UUID userId);
}
