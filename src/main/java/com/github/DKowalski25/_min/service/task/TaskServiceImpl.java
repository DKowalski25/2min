package com.github.DKowalski25._min.service.task;

import com.github.DKowalski25._min.dto.task.TaskMapper;
import com.github.DKowalski25._min.dto.task.TaskRequestDTO;
import com.github.DKowalski25._min.dto.task.TaskResponseDTO;
import com.github.DKowalski25._min.dto.task.TaskUpdateDTO;
import com.github.DKowalski25._min.exceptions.AccessDeniedException;
import com.github.DKowalski25._min.exceptions.EntityNotFoundException;
import com.github.DKowalski25._min.models.Task;
import com.github.DKowalski25._min.models.TimeBlock;
import com.github.DKowalski25._min.models.User;
import com.github.DKowalski25._min.repository.task.TaskRepository;

import com.github.DKowalski25._min.repository.timeblock.TimeBlockRepository;
import com.github.DKowalski25._min.repository.user.UserRepository;

import lombok.RequiredArgsConstructor;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

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

        Task task = taskMapper.toEntity(taskRequestDTO);
        task.setUser(user);
        task.setTimeBlock(timeBlock);
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
    public List<TaskResponseDTO> getAllTasks(UUID userId) {
        return taskRepository.findByUserId(userId).stream()
                .map(taskMapper::toResponse)
                .toList();
    }

    @Override
    @Transactional
    public TaskResponseDTO updateTask(UUID id, TaskUpdateDTO taskUpdateDTO, UUID userId) {
        Task task = taskRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Task not found", id));

        if (task.getUser().getId() != userId) {
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
}
