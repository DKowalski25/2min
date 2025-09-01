package com.github.DKowalski25._min.repository.task;

import com.github.DKowalski25._min.controller.task.TaskController;
import com.github.DKowalski25._min.models.Task;

import com.github.DKowalski25._min.models.TimeBlock;
import com.github.DKowalski25._min.models.User;
import com.github.DKowalski25._min.service.task.TaskService;
import org.springframework.data.jpa.repository.JpaRepository;

import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
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
    List<Task> findByUserIdAndArchivedAtIsNull(UUID userId);
    List<Task> findByUserIdAndArchivedAtIsNotNull(UUID userId);
    List<Task> findByUserIdAndPlannedForNext(UUID userId, boolean plannedForNext);
    List<Task> findByUserIdAndArchivedAtBefore(UUID userId, LocalDateTime date);

    @Modifying
    @Query("DELETE FROM Task t WHERE t.user = :user AND t.archivedAt < :date")
    void deleteOldHistory(@Param("user") User user, @Param("date") LocalDateTime date);

    @Modifying
    @Query("DELETE FROM Task t WHERE t.user = :user AND t.archivedAt IS NOT NULL")
    void deleteAllHistory(@Param("user") User user);

    @Query("SELECT t FROM Task t WHERE t.timeBlock = :timeBlock AND t.plannedForNext = true")
    List<Task> findPlannedTasksByTimeBlock(@Param("timeBlock") TimeBlock timeBlock);

    @Query("SELECT t FROM Task t WHERE t.timeBlock = :timeBlock AND t.plannedForNext = :plannedForNext")
    List<Task> findTimeBlockAndPlanned(@Param("timeBlock") TimeBlock timeBlock,
                                       @Param("plannedForNext") boolean plannedForNext);

    // Найти задачи по TimeBlock и статусу plannedForNext
    @Query("SELECT t FROM Task t WHERE t.timeBlock = :timeBlock AND t.plannedForNext = :plannedForNext AND t.archivedAt IS NULL")
    List<Task> findByTimeBlockAndPlannedForNext(@Param("timeBlock") TimeBlock timeBlock,
                                                @Param("plannedForNext") boolean plannedForNext);


}
