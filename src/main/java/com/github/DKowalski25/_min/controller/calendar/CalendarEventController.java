package com.github.DKowalski25._min.controller.calendar;

import com.github.DKowalski25._min.dto.calendar.CalendarEventRequestDTO;
import com.github.DKowalski25._min.dto.calendar.CalendarEventResponseDTO;
import com.github.DKowalski25._min.dto.calendar.CalendarEventUpdateDTO;

import com.github.DKowalski25._min.repository.calendar.CalendarEventRepository;
import com.github.DKowalski25._min.service.calendar.CalendarEventService;
import jakarta.validation.Valid;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

/**
 * REST controller for managing calendar event operations.
 *
 * <p>Provides endpoints for calendar event CRUD operations and retrieval by user.
 * All methods return {@link ResponseEntity} with appropriate HTTP status codes.</p>
 *
 * <p><strong>Usage example:</strong>
 * <pre>{@code
 * // Create event
 * CalendarEventRequestDTO newEvent = new CalendarEventRequestDTO(...);
 * ResponseEntity<CalendarEventResponseDTO> response = calendarEventController
 *     .createEvent(newEvent, userId);
 * }</pre></p>
 *
 * @see CalendarEventService
 * @see CalendarEventRepository
 * @see CalendarEventRequestDTO
 * @see CalendarEventResponseDTO
 * @see CalendarEventUpdateDTO
 */
public interface CalendarEventController {

    /**
     * Creates a new calendar event for specified user.
     *
     * @param calendarEventRequestDTO the event creation data (must not be {@code null})
     * @param userId the owner user identifier (must be positive)
     * @return {@link ResponseEntity} with created event data and HTTP status 201 (Created),
     *         or status 404 (Not Found) if user doesn't exist
     * @throws jakarta.validation.ValidationException if DTO validation fails
     */
    @PostMapping("/{userId}")
    ResponseEntity<CalendarEventResponseDTO> createEvent(
            @RequestBody @Valid CalendarEventRequestDTO calendarEventRequestDTO, @PathVariable int userId);

    /**
     * Retrieves all calendar events for specified user.
     *
     * @param userId the user identifier (must be positive)
     * @return {@link ResponseEntity} with list of user's events and HTTP status 200 (OK),
     *         or empty list if no events exist
     */
    @GetMapping("/{userId}")
    ResponseEntity<List<CalendarEventResponseDTO>> getUserEvents(@PathVariable int userId);

    /**
     * Updates an existing calendar event.
     *
     * @param calendarEventUpdateDTO the event update data (must not be {@code null})
     * @param eventId the event identifier to update (must be positive)
     * @return {@link ResponseEntity} with updated event data and HTTP status 200 (OK),
     *         or status 404 (Not Found) if event doesn't exist
     * @throws jakarta.validation.ValidationException if DTO validation fails
     */
    ResponseEntity<CalendarEventResponseDTO> updateCalendarEvent(
            CustomUserDetails userDetails,
            CalendarEventUpdateDTO calendarEventUpdateDTO,
            UUID eventId);

    /**
     * Deletes a calendar event by its identifier.
     *
     * @param eventId the event identifier to delete (must be positive)
     * @return {@link ResponseEntity} with HTTP status 204 (No Content) if successful,
     *         or status 404 (Not Found) if event doesn't exist
     */
    @DeleteMapping("{eventId}")
    ResponseEntity<Void> deleteCalendarEvent(@PathVariable int eventId);


}
