package com.github.DKowalski25._min.integration.task;


import com.github.DKowalski25._min.dto.task.TaskRequestDTO;
import com.github.DKowalski25._min.dto.task.TaskResponseDTO;
import com.github.DKowalski25._min.dto.user.UserRequestDTO;
import com.github.DKowalski25._min.integration.AbstractIntegrationTest;
import com.github.DKowalski25._min.models.Tag;
import com.github.DKowalski25._min.service.task.TaskServiceImpl;
import com.github.DKowalski25._min.service.user.UserService;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;

class TaskServiceIntegrationTest extends AbstractIntegrationTest {

    @Autowired
    private TaskServiceImpl taskService;

    @Autowired
    private UserService userService;

    private UUID userId;
    private UUID timeBlockId;

    @BeforeEach
    @Transactional
    void setUp() {
        UserRequestDTO userRequest = new UserRequestDTO("taskuser", "password123", "task@test.com");
        var user = userService.createUser(userRequest);
        userId = user.id();

        // Assuming TimeBlocks are created automatically during user creation
        // We'll get the first time block for testing
        var tasks = taskService.getAllTasks(userId);
        if (!tasks.isEmpty()) {
            timeBlockId = tasks.get(0).id();
        }
    }

    @Test
    @Transactional
    void createTask_ShouldPersistTaskInDatabase() {
        TaskRequestDTO request = new TaskRequestDTO(
                "Integration Task",
                "Test Description",
                Tag.MAIN,
                timeBlockId
        );

        TaskResponseDTO result = taskService.createTask(request, userId);

        assertNotNull(result.id());
        assertEquals("Integration Task", result.title());
        assertEquals(Tag.MAIN, result.tag());
        assertEquals(userId, result.userId());
    }

    @Test
    @Transactional
    void getAllTasks_ShouldReturnUserTasks() {
        TaskRequestDTO request = new TaskRequestDTO(
                "Test Task",
                "Description",
                Tag.MAIN,
                timeBlockId
        );
        taskService.createTask(request, userId);

        List<TaskResponseDTO> tasks = taskService.getAllTasks(userId);

        assertFalse(tasks.isEmpty());
        assertEquals("Test Task", tasks.get(0).title());
    }

    @Test
    @Transactional
    void updateTask_ShouldUpdateTaskProperties() {
        TaskRequestDTO createRequest = new TaskRequestDTO(
                "Original Task",
                "Original Desc",
                Tag.MAIN,
                timeBlockId
        );
        TaskResponseDTO created = taskService.createTask(createRequest, userId);

        // Update the task
        var updateRequest = new com.github.DKowalski25._min.dto.task.TaskUpdateDTO(
                "Updated Task",
                "Updated Desc",
                Tag.MAIN,
                true
        );

        TaskResponseDTO updated = taskService.updateTask(created.id(), updateRequest, userId);

        assertEquals("Updated Task", updated.title());
        assertEquals("Updated Desc", updated.description());
        assertEquals(Tag.MAIN, updated.tag());
        assertTrue(updated.done());
    }
}
