package com.github.DKowalski25._min.service.task;

import com.github.DKowalski25._min.dto.task.TaskMapper;
import com.github.DKowalski25._min.dto.task.TaskRequestDTO;
import com.github.DKowalski25._min.dto.task.TaskResponseDTO;
import com.github.DKowalski25._min.dto.task.TaskUpdateDTO;
import com.github.DKowalski25._min.exceptions.EntityNotFoundException;
import com.github.DKowalski25._min.models.Task;
import com.github.DKowalski25._min.repository.task.TaskRepository;

import lombok.RequiredArgsConstructor;

import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class TaskServiceImpl implements TaskService {
    private final TaskRepository taskRepository;
    private final TaskMapper taskMapper;

    @Override
    public TaskResponseDTO createTask(TaskRequestDTO taskRequestDTO, int userId) {
        Task enrichedTask = taskMapper.toEntityWithUser(taskRequestDTO, userId);
        Task task = taskRepository.save(enrichedTask);
        return taskMapper.toResponse(task);
    }

    @Override
    public TaskResponseDTO getTaskById(int id) {
        Task task = taskRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Task not found", id));
        return taskMapper.toResponse(task);
    }

    @Override
    public List<TaskResponseDTO> getAllTasks(int userId) {
        return taskRepository.findByUserId(userId).stream()
                .map(taskMapper::toResponse)
                .toList();
    }

    @Override
    public TaskResponseDTO updateTask(int id, TaskUpdateDTO taskUpdateDTO) {
        Task task = taskRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Task not found", id));

        taskMapper.updateFromDto(taskUpdateDTO, task);
        Task saved = taskRepository.save(task);
        return taskMapper.toResponse(saved);
    }

    @Override
    public void deleteTask(int id) {
        if (!taskRepository.existsById(id)) {
            throw new EntityNotFoundException("Task", id);
        }
        taskRepository.deleteById(id);
    }
}
