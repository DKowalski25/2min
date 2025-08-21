package com.github.DKowalski25._min.unit.task;

import com.github.DKowalski25._min.dto.task.TaskRequestDTO;
import com.github.DKowalski25._min.dto.task.TaskResponseDTO;
import com.github.DKowalski25._min.exceptions.AccessDeniedException;
import com.github.DKowalski25._min.exceptions.EntityNotFoundException;
import com.github.DKowalski25._min.models.*;
import com.github.DKowalski25._min.repository.task.TaskRepository;
import com.github.DKowalski25._min.repository.timeblock.TimeBlockRepository;
import com.github.DKowalski25._min.repository.user.UserRepository;

import com.github.DKowalski25._min.service.task.TaskServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;

import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class TaskServiceUnitTest {

    @Mock
    private TaskRepository taskRepository;

    @Mock
    private UserRepository userRepository;

    @Mock
    private TimeBlockRepository timeBlockRepository;

    @InjectMocks
    private TaskServiceImpl taskService;

    private User testUser;
    private TimeBlock testTimeBlock;
    private Task testTask;
    private UUID userId;

    @BeforeEach
    void setUp() {
        userId = UUID.randomUUID();

        testUser = User.builder()
                .id(userId)
                .username("testuser")
                .email("test@email.com")
                .build();

        testTimeBlock = TimeBlock.builder()
                .id(UUID.randomUUID())
                .type(TimeType.DAY)
                .user(testUser)
                .build();

        testTask = Task.builder()
                .id(UUID.randomUUID())
                .title("Test Task")
                .description("Test Description")
                .tag(Tag.MAIN)
                .done(false)
                .user(testUser)
                .timeBlock(testTimeBlock)
                .build();
    }

    @Test
    void createTask_ShouldCreateTaskSuccessfully() {
        TaskRequestDTO request = new TaskRequestDTO("New Task", "Description", Tag.MAIN, testTimeBlock.getId());

        when(userRepository.findById(userId)).thenReturn(Optional.of(testUser));
        when(timeBlockRepository.findById(testTimeBlock.getId())).thenReturn(Optional.of(testTimeBlock));
        when(taskRepository.save(any())).thenReturn(testTask);

        TaskResponseDTO result = taskService.createTask(request, userId);

        assertNotNull(result);
        assertEquals("Test Task", result.title());
    }

    @Test
    void createTask_ShouldThrowExceptionForInvalidTimeBlock() {
        TaskRequestDTO request = new TaskRequestDTO("New Task", "Description", Tag.MAIN, UUID.randomUUID());

        when(userRepository.findById(userId)).thenReturn(Optional.of(testUser));
        when(timeBlockRepository.findById(any())).thenReturn(Optional.empty());

        assertThrows(EntityNotFoundException.class, () ->
                taskService.createTask(request, userId));
    }

    @Test
    void getTaskById_ShouldReturnTask() {
        when(taskRepository.findById(testTask.getId())).thenReturn(Optional.of(testTask));

        TaskResponseDTO result = taskService.getTaskById(testTask.getId(), userId);

        assertNotNull(result);
        assertEquals(testTask.getId(), result.id());
    }

    @Test
    void getTaskById_ShouldThrowExceptionForWrongUser() {
        UUID otherUserId = UUID.randomUUID();
        when(taskRepository.findById(testTask.getId())).thenReturn(Optional.of(testTask));

        assertThrows(AccessDeniedException.class, () ->
                taskService.getTaskById(testTask.getId(), otherUserId));
    }

    @Test
    void getAllTasks_ShouldReturnUserTasks() {
        when(taskRepository.findByUserId(userId)).thenReturn(List.of(testTask));

        List<TaskResponseDTO> result = taskService.getAllTasks(userId);

        assertEquals(1, result.size());
        assertEquals(testTask.getId(), result.get(0).id());
    }
}