package com.github.DKowalski25._min.service.task;

import com.github.DKowalski25._min.dto.task.TaskRequestDTO;
import com.github.DKowalski25._min.dto.task.TaskResponseDTO;
import com.github.DKowalski25._min.dto.task.TaskUpdateDTO;

import java.util.List;

public interface TaskService {
    TaskResponseDTO createTask(TaskRequestDTO taskRequestDTO);
    TaskResponseDTO getTaskById(int id);
    List<TaskResponseDTO> getAllTasks();
    TaskResponseDTO updateTask(int id, TaskUpdateDTO taskUpdateDTO);
    void deleteTask(int id);
}
