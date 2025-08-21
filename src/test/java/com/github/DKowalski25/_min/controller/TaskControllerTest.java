package com.github.DKowalski25._min.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.github.DKowalski25._min.config.security.jwt.JwtUtil;
import com.github.DKowalski25._min.controller.task.TaskControllerImpl;
import com.github.DKowalski25._min.dto.task.TaskRequestDTO;
import com.github.DKowalski25._min.dto.task.TaskResponseDTO;
import com.github.DKowalski25._min.models.Tag;
import com.github.DKowalski25._min.models.TimeType;
import com.github.DKowalski25._min.service.task.TaskService;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;

import java.util.List;
import java.util.UUID;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(TaskControllerImpl.class)
class TaskControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @MockBean
    private TaskService taskService;

    @MockBean
    private AuthenticationManager authenticationManager;

    @MockBean
    private JwtUtil jwtUtil;

    private final UUID userId = UUID.randomUUID();
    private final UUID taskId = UUID.randomUUID();
    private final UUID timeBlockId = UUID.randomUUID();

    @Test
    @WithMockUser
    void createTask_ShouldReturnCreated() throws Exception {
        TaskRequestDTO request = new TaskRequestDTO(
                "Test Task", "Description", Tag.MAIN, timeBlockId
        );

        TaskResponseDTO response = new TaskResponseDTO(
                taskId, "Test Task", "Description", Tag.MAIN, false, TimeType.DAY, userId
        );

        when(taskService.createTask(any(), any())).thenReturn(response);

        mockMvc.perform(post("/api/v1/tasks")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.id").exists())
                .andExpect(jsonPath("$.title").value("Test Task"));
    }

    @Test
    @WithMockUser
    void getTaskById_ShouldReturnTask() throws Exception {
        TaskResponseDTO response = new TaskResponseDTO(
                taskId, "Test Task", "Description", Tag.MAIN, false, TimeType.DAY, userId
        );

        when(taskService.getTaskById(taskId, userId)).thenReturn(response);

        mockMvc.perform(get("/api/v1/tasks/{id}", taskId))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(taskId.toString()))
                .andExpect(jsonPath("$.title").value("Test Task"));
    }

    @Test
    @WithMockUser
    void getAllTasks_ShouldReturnTasksList() throws Exception {
        TaskResponseDTO task1 = new TaskResponseDTO(
                taskId, "Task 1", "Desc 1", Tag.MAIN, false, TimeType.DAY, userId
        );
        TaskResponseDTO task2 = new TaskResponseDTO(
                UUID.randomUUID(), "Task 2", "Desc 2", Tag.MAIN, true, TimeType.WEEK, userId
        );

        when(taskService.getAllTasks(userId)).thenReturn(List.of(task1, task2));

        mockMvc.perform(get("/api/v1/tasks"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.length()").value(2))
                .andExpect(jsonPath("$[0].title").value("Task 1"))
                .andExpect(jsonPath("$[1].title").value("Task 2"));
    }

    @Test
    @WithMockUser
    void deleteTask_ShouldReturnNoContent() throws Exception {
        mockMvc.perform(delete("/api/v1/tasks/{id}", taskId))
                .andExpect(status().isNoContent());
    }
}