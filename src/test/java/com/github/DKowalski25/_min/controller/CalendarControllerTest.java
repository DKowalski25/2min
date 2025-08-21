package com.github.DKowalski25._min.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.github.DKowalski25._min.config.security.jwt.JwtUtil;
import com.github.DKowalski25._min.controller.calendar.CalendarEventControllerImpl;
import com.github.DKowalski25._min.dto.calendar.CalendarEventRequestDTO;
import com.github.DKowalski25._min.dto.calendar.CalendarEventResponseDTO;
import com.github.DKowalski25._min.dto.user.UserResponseDTO;
import com.github.DKowalski25._min.models.UserRole;
import com.github.DKowalski25._min.service.calendar.CalendarEventService;
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

@WebMvcTest(CalendarEventControllerImpl.class)
class CalendarControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @MockBean
    private CalendarEventService calendarEventService;

    @MockBean
    private AuthenticationManager authenticationManager;

    @MockBean
    private JwtUtil jwtUtil;

    private final UUID userId = UUID.randomUUID();
    private final UUID eventId = UUID.randomUUID();

    @Test
    @WithMockUser
    void createEvent_ShouldReturnCreated() throws Exception {
        CalendarEventRequestDTO request = new CalendarEventRequestDTO(
                "Meeting", "Team meeting", "2024-01-01T10:00:00", "2024-01-01T11:00:00"
        );

        UserResponseDTO organizer = new UserResponseDTO(userId, "user", "user@test.com", UserRole.USER, false);
        CalendarEventResponseDTO response = new CalendarEventResponseDTO(
                eventId, "Meeting", "Team meeting", "2024-01-01T10:00:00", "2024-01-01T11:00:00", organizer
        );

        when(calendarEventService.createEvent(any(), any())).thenReturn(response);

        mockMvc.perform(post("/api/v1/calendars")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.id").exists())
                .andExpect(jsonPath("$.title").value("Meeting"))
                .andExpect(jsonPath("$.organizer.id").value(userId.toString()));
    }

    @Test
    @WithMockUser
    void getUserEvents_ShouldReturnEventsList() throws Exception {
        UserResponseDTO organizer = new UserResponseDTO(userId, "user", "user@test.com", UserRole.USER, false);
        CalendarEventResponseDTO event1 = new CalendarEventResponseDTO(
                eventId, "Meeting 1", "Desc 1", "2024-01-01T10:00:00", "2024-01-01T11:00:00", organizer
        );
        CalendarEventResponseDTO event2 = new CalendarEventResponseDTO(
                UUID.randomUUID(), "Meeting 2", "Desc 2", "2024-01-02T10:00:00", "2024-01-02T11:00:00", organizer
        );

        when(calendarEventService.getUserEvents(userId)).thenReturn(List.of(event1, event2));

        mockMvc.perform(get("/api/v1/calendars"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.length()").value(2))
                .andExpect(jsonPath("$[0].title").value("Meeting 1"))
                .andExpect(jsonPath("$[1].title").value("Meeting 2"));
    }

    @Test
    @WithMockUser
    void deleteEvent_ShouldReturnNoContent() throws Exception {
        mockMvc.perform(delete("/api/v1/calendars/{eventId}", eventId))
                .andExpect(status().isNoContent());
    }

    @Test
    @WithMockUser
    void updateEvent_ShouldReturnUpdatedEvent() throws Exception {
        var updateRequest = new com.github.DKowalski25._min.dto.calendar.CalendarEventUpdateDTO(
                "Updated Meeting", "Updated Desc", "2024-01-01T12:00:00", "2024-01-01T13:00:00"
        );

        UserResponseDTO organizer = new UserResponseDTO(userId, "user", "user@test.com", UserRole.USER, false);
        CalendarEventResponseDTO response = new CalendarEventResponseDTO(
                eventId, "Updated Meeting", "Updated Desc", "2024-01-01T12:00:00", "2024-01-01T13:00:00", organizer
        );

        when(calendarEventService.updateEvent(any(), any(), any())).thenReturn(response);

        mockMvc.perform(patch("/api/v1/calendars/update/{eventId}", eventId)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(updateRequest)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.title").value("Updated Meeting"))
                .andExpect(jsonPath("$.description").value("Updated Desc"));
    }
}