package com.github.DKowalski25._min.service.task;

import com.github.DKowalski25._min.dto.task.TaskMapper;
import com.github.DKowalski25._min.dto.task.TaskRequestDTO;
import com.github.DKowalski25._min.dto.task.TaskResponseDTO;
import com.github.DKowalski25._min.dto.task.TaskUpdateDTO;
import com.github.DKowalski25._min.exceptions.EntityNotFoundException;
import com.github.DKowalski25._min.models.Task;
import com.github.DKowalski25._min.models.TimeBlock;
import com.github.DKowalski25._min.repository.task.TaskRepository;

import com.github.DKowalski25._min.repository.timeblock.TimeBlockRepository;
import lombok.RequiredArgsConstructor;

import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class TaskServiceImpl implements TaskService {
    private final TaskRepository taskRepository;
    private final TaskMapper taskMapper;
    private final TimeBlockRepository timeBlockRepository;

    @Override
    public TaskResponseDTO createTask(TaskRequestDTO taskRequestDTO) {
        Task task = taskMapper.toEntity(taskRequestDTO);
        taskRepository.save(task);
        return taskMapper.toResponse(task);
    }

    @Override
    public TaskResponseDTO getTaskById(int id) {
        Task task = taskRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Task not found", id));
        return taskMapper.toResponse(task);
    }

    @Override
    public List<TaskResponseDTO> getAllTasks() {
        return taskRepository.findAll().stream()
                .map(taskMapper::toResponse)
                .toList();
    }

    @Override
    public TaskResponseDTO updateTask(int id, TaskUpdateDTO taskUpdateDTO) {
        Task task = taskRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Task not found", id));

        if (taskUpdateDTO.title() != null) task.setTitle(taskUpdateDTO.title());
        if (taskUpdateDTO.description() != null) task.setDescription(taskUpdateDTO.description());
        if (taskUpdateDTO.tag() != null) task.setTag(taskUpdateDTO.tag());
        if (taskUpdateDTO.isDone() != null) task.setDone(taskUpdateDTO.isDone());
        if (taskUpdateDTO.timeBlockType() != null) {
            TimeBlock timeBlock = timeBlockRepository.findByType(taskUpdateDTO.timeBlockType())
                    .orElseThrow(() -> new EntityNotFoundException("TimeBlock not found", taskUpdateDTO.timeBlockType()));
            task.setTimeBlock(timeBlock);
        }

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
