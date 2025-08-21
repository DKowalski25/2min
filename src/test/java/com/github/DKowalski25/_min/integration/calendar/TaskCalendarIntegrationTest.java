package com.github.DKowalski25._min.integration.calendar;

import com.github.DKowalski25._min.dto.calendar.CalendarEventRequestDTO;
import com.github.DKowalski25._min.dto.calendar.CalendarEventResponseDTO;
import com.github.DKowalski25._min.dto.user.UserRequestDTO;
import com.github.DKowalski25._min.integration.AbstractIntegrationTest;
import com.github.DKowalski25._min.service.calendar.CalendarEventServiceImpl;
import com.github.DKowalski25._min.service.user.UserService;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;

class CalendarServiceIntegrationTest extends AbstractIntegrationTest {

    @Autowired
    private CalendarEventServiceImpl calendarEventService;

    @Autowired
    private UserService userService;

    private UUID userId;

    @BeforeEach
    @Transactional
    void setUp() {
        UserRequestDTO userRequest = new UserRequestDTO("calendaruser", "password123", "calendar@test.com");
        var user = userService.createUser(userRequest);
        userId = user.id();
    }

    @Test
    @Transactional
    void createEvent_ShouldPersistCalendarEvent() {
        CalendarEventRequestDTO request = new CalendarEventRequestDTO(
                "Meeting",
                "Team meeting",
                "2024-01-01T10:00:00",
                "2024-01-01T11:00:00"
        );

        CalendarEventResponseDTO result = calendarEventService.createEvent(request, userId);

        assertNotNull(result.id());
        assertEquals("Meeting", result.title());
        assertEquals("Team meeting", result.description());
        assertEquals(userId, result.organizer().id());
    }

    @Test
    @Transactional
    void getUserEvents_ShouldReturnOnlyUserEvents() {
        // Create first event
        CalendarEventRequestDTO request1 = new CalendarEventRequestDTO(
                "Meeting 1", "Desc 1", "2024-01-01T10:00:00", "2024-01-01T11:00:00"
        );
        calendarEventService.createEvent(request1, userId);

        // Create second event
        CalendarEventRequestDTO request2 = new CalendarEventRequestDTO(
                "Meeting 2", "Desc 2", "2024-01-02T10:00:00", "2024-01-02T11:00:00"
        );
        calendarEventService.createEvent(request2, userId);

        List<CalendarEventResponseDTO> events = calendarEventService.getUserEvents(userId);

        assertEquals(2, events.size());
        assertTrue(events.stream().allMatch(event -> event.organizer().id().equals(userId)));
    }

    @Test
    @Transactional
    void updateEvent_ShouldUpdateEventProperties() {
        // Create event
        CalendarEventRequestDTO createRequest = new CalendarEventRequestDTO(
                "Original", "Original Desc", "2024-01-01T10:00:00", "2024-01-01T11:00:00"
        );
        CalendarEventResponseDTO created = calendarEventService.createEvent(createRequest, userId);

        // Update event
        var updateRequest = new com.github.DKowalski25._min.dto.calendar.CalendarEventUpdateDTO(
                "Updated Title",
                "Updated Description",
                "2024-01-01T12:00:00",
                "2024-01-01T13:00:00"
        );

        CalendarEventResponseDTO updated = calendarEventService.updateEvent(updateRequest, created.id(), userId);

        assertEquals("Updated Title", updated.title());
        assertEquals("Updated Description", updated.description());
    }

    @Test
    @Transactional
    void deleteEvent_ShouldRemoveEvent() {
        CalendarEventRequestDTO request = new CalendarEventRequestDTO(
                "To Delete", "Delete me", "2024-01-01T10:00:00", "2024-01-01T11:00:00"
        );
        CalendarEventResponseDTO created = calendarEventService.createEvent(request, userId);

        // Verify event exists
        List<CalendarEventResponseDTO> eventsBefore = calendarEventService.getUserEvents(userId);
        assertEquals(1, eventsBefore.size());

        // Delete event
        calendarEventService.deleteEvent(created.id(), userId);

        // Verify event is gone
        List<CalendarEventResponseDTO> eventsAfter = calendarEventService.getUserEvents(userId);
        assertTrue(eventsAfter.isEmpty());
    }
}