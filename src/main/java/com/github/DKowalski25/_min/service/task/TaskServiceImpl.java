package com.github.DKowalski25._min.service.task;

import com.github.DKowalski25._min.dto.task.TaskMapper;
import com.github.DKowalski25._min.dto.task.TaskRequestDTO;
import com.github.DKowalski25._min.dto.task.TaskResponseDTO;
import com.github.DKowalski25._min.dto.task.TaskUpdateDTO;
import com.github.DKowalski25._min.exceptions.AccessDeniedException;
import com.github.DKowalski25._min.exceptions.EntityNotFoundException;
import com.github.DKowalski25._min.models.Task;
import com.github.DKowalski25._min.models.TimeBlock;
import com.github.DKowalski25._min.models.TimeType;
import com.github.DKowalski25._min.models.User;
import com.github.DKowalski25._min.repository.task.TaskRepository;

import com.github.DKowalski25._min.repository.timeblock.TimeBlockRepository;
import com.github.DKowalski25._min.repository.user.UserRepository;

import lombok.RequiredArgsConstructor;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.temporal.WeekFields;
import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class TaskServiceImpl implements TaskService {
    private final TaskRepository taskRepository;
    private final TaskMapper taskMapper;
    private final UserRepository userRepository;
    private final TimeBlockRepository timeBlockRepository;

    @Override
    @Transactional
    public TaskResponseDTO createTask(TaskRequestDTO taskRequestDTO, UUID userId) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new EntityNotFoundException("User not found", userId));
        TimeBlock timeBlock = timeBlockRepository.findById(taskRequestDTO.timeBlockId())
                .orElseThrow(() -> new EntityNotFoundException("TimeBlock not found", taskRequestDTO.timeBlockId()));

        if (!timeBlock.getUser().getId().equals(userId)) {
            throw new AccessDeniedException("You don't have permission to create this task");
        }

        boolean plannedForNext = Boolean.TRUE.equals(taskRequestDTO.plannedForNext());

        Task task = taskMapper.toEntity(taskRequestDTO);
        task.setUser(user);
        task.setTimeBlock(timeBlock);
        task.setPlannedForNext(plannedForNext);
        task.setPeriodMarker(generatePeriodMarker(timeBlock.getType(), plannedForNext));

        Task saved = taskRepository.save(task);
        return taskMapper.toResponse(saved);
    }


    @Override
    @Transactional(readOnly = true)
    public TaskResponseDTO getTaskById(UUID id, UUID userId) {
        Task task = taskRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Task not found", id));
        if (!task.getUser().getId().equals(userId)) {
            throw new AccessDeniedException("Access to task denied");
        }
        return taskMapper.toResponse(task);
    }

    @Override
    @Transactional(readOnly = true)
    public List<TaskResponseDTO> getTasks(UUID userId, Boolean includeHistory) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new EntityNotFoundException("User not found", userId));

        List<Task> tasks;
        if (Boolean.TRUE.equals(includeHistory)) {
            tasks = taskRepository.findByUserId(userId);
        } else {
            tasks = taskRepository.findByUserIdAndArchivedAtIsNull(userId);
        }

        return tasks.stream().map(taskMapper::toResponse).toList();
    }

    @Override
    public List<TaskResponseDTO> getPlannedForNextTasks(UUID userId) {
        List<Task> tasks = taskRepository.findByUserIdAndPlannedForNext(userId, true);
        return tasks.stream().map(taskMapper::toResponse).toList();
    }

    @Override
    public void cleanHistory(UUID userId, LocalDateTime olderThan) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new EntityNotFoundException("User not found", userId));

        if (olderThan != null) {
            taskRepository.deleteOldHistory(user, olderThan);
        } else {
            taskRepository.deleteAllHistory(user);
        }
    }

    @Override
    @Transactional
    public TaskResponseDTO updateTask(UUID id, TaskUpdateDTO taskUpdateDTO, UUID userId) {
        Task task = taskRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Task not found", id));

        if (!task.getUser().getId().equals(userId)) {
            throw new AccessDeniedException("You don't have permission to update this task");
        }

        taskMapper.updateFromDto(taskUpdateDTO, task);
        Task saved = taskRepository.save(task);
        return taskMapper.toResponse(saved);
    }

    @Override
    @Transactional
    public void deleteTask(UUID id, UUID userId) {
        Task task = taskRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Task not found", id));

        if (!task.getUser().getId().equals(userId)) {
            throw new AccessDeniedException("You don't have permission to delete this task");
        }
        taskRepository.deleteById(id);
    }

    private String generatePeriodMarker(TimeType type, boolean plannedForNext) {
        LocalDate baseDate = LocalDate.now();

        if (plannedForNext) {
            switch (type) {
                case DAY:
                    baseDate = baseDate.plusDays(1);
                    break;
                case WEEK:
                    baseDate = baseDate.plusWeeks(1);
                    break;
                case MONTH:
                    baseDate = baseDate.plusMonths(1);
                    break;
            }
        }

        switch (type) {
            case DAY:
                return baseDate.format(DateTimeFormatter.ISO_LOCAL_DATE);
            case WEEK:
                int weekNumber = baseDate.get(WeekFields.ISO.weekOfWeekBasedYear());
                return baseDate.getYear() + "-W" + String.format("%02d", weekNumber);
            case MONTH:
                return baseDate.getYear() + "-" + String.format("%02d", baseDate.getMonthValue());
            default:
                throw new IllegalArgumentException("Unknown TimeType: " + type);
        }
    }
}
