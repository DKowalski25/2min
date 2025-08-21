package com.github.DKowalski25._min.unit.calendar;

import com.github.DKowalski25._min.dto.calendar.CalendarEventRequestDTO;
import com.github.DKowalski25._min.dto.calendar.CalendarEventResponseDTO;
import com.github.DKowalski25._min.exceptions.AccessDeniedException;
import com.github.DKowalski25._min.models.CalendarEvent;
import com.github.DKowalski25._min.models.User;
import com.github.DKowalski25._min.repository.calendar.CalendarEventRepository;
import com.github.DKowalski25._min.repository.user.UserRepository;
import com.github.DKowalski25._min.service.calendar.CalendarEventServiceImpl;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;

import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class CalendarEventServiceUnitTest {

    @Mock
    private CalendarEventRepository calendarEventRepository;

    @Mock
    private UserRepository userRepository;

    @InjectMocks
    private CalendarEventServiceImpl calendarEventService;

    private User testUser;
    private CalendarEvent testEvent;
    private UUID userId;

    @BeforeEach
    void setUp() {
        userId = UUID.randomUUID();

        testUser = User.builder()
                .id(userId)
                .username("testuser")
                .email("test@email.com")
                .build();

        testEvent = CalendarEvent.builder()
                .id(UUID.randomUUID())
                .title("Test Event")
                .description("Test Description")
                .startEvent(LocalDateTime.now())
                .endEvent(LocalDateTime.now().plusHours(1))
                .user(testUser)
                .build();
    }

    @Test
    void createEvent_ShouldCreateEventSuccessfully() {
        CalendarEventRequestDTO request = new CalendarEventRequestDTO(
                "New Event",
                "Description",
                "2024-01-01T10:00:00",
                "2024-01-01T11:00:00"
        );

        when(userRepository.findById(userId)).thenReturn(Optional.of(testUser));
        when(calendarEventRepository.save(any())).thenReturn(testEvent);

        CalendarEventResponseDTO result = calendarEventService.createEvent(request, userId);

        assertNotNull(result);
        assertEquals("Test Event", result.title());
    }

    @Test
    void getUserEvents_ShouldReturnUserEvents() {
        when(calendarEventRepository.findByUserId(userId)).thenReturn(List.of(testEvent));

        List<CalendarEventResponseDTO> result = calendarEventService.getUserEvents(userId);

        assertEquals(1, result.size());
        assertEquals(testEvent.getId(), result.get(0).id());
    }

    @Test
    void deleteEvent_ShouldDeleteEvent() {
        when(calendarEventRepository.findById(testEvent.getId())).thenReturn(Optional.of(testEvent));
        doNothing().when(calendarEventRepository).deleteEventById(any());

        assertDoesNotThrow(() -> calendarEventService.deleteEvent(testEvent.getId(), userId));
        verify(calendarEventRepository).deleteEventById(testEvent.getId());
    }

    @Test
    void deleteEvent_ShouldThrowExceptionForWrongUser() {
        UUID wrongUserId = UUID.randomUUID();
        when(calendarEventRepository.findById(testEvent.getId())).thenReturn(Optional.of(testEvent));

        assertThrows(AccessDeniedException.class, () ->
                calendarEventService.deleteEvent(testEvent.getId(), wrongUserId));
    }
}