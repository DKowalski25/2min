package com.github.DKowalski25._min.service.calendar;

import com.github.DKowalski25._min.controller.calendar.CalendarEventController;
import com.github.DKowalski25._min.dto.calendar.CalendarEventRequestDTO;
import com.github.DKowalski25._min.dto.calendar.CalendarEventResponseDTO;
import com.github.DKowalski25._min.dto.calendar.CalendarEventUpdateDTO;
import com.github.DKowalski25._min.exceptions.EntityNotFoundException;
import com.github.DKowalski25._min.repository.calendar.CalendarEventRepository;

import java.util.List;

/**
 * Service interface for managing {@link com.github.DKowalski25._min.models.CalendarEvent} entities.
 *
 * <p>Provides business logic operations for calendar event management including creation, retrieval,
 * update and deletion of events. All methods work with Data Transfer Objects (DTOs) to
 * decouple the service layer from persistence entities.</p>
 *
 * <p><strong>Usage Example:</strong>
 * <pre>{@code
 * // Creating an event
 * CalendarEventRequestDTO newEvent = new CalendarEventRequestDTO("Meeting", "Team sync",
 *     "2023-10-10T10:00:00", "2023-10-10T11:00:00");
 * CalendarEventResponseDTO createdEvent = calendarEventService.createEvent(newEvent, userId);
 *
 * // Retrieving user events
 * List<CalendarEventResponseDTO> events = calendarEventService.getUserEvents(userId);
 * }</pre>
 * </p>
 *
 * @see CalendarEventController
 * @see CalendarEventRepository
 * @see CalendarEventRequestDTO
 * @see CalendarEventResponseDTO
 * @see CalendarEventUpdateDTO
 */
public interface CalendarEventService {

    /**
     * Retrieves all calendar events for specified user.
     *
     * @param userId the user identifier (must be positive)
     * @return list of user's calendar events (never {@code null}, may be empty)
     * @throws IllegalArgumentException if userId is not positive
     */
    List<CalendarEventResponseDTO> getUserEvents(int userId);

    /**
     * Creates a new calendar event.
     *
     * @param calendarEventRequestDTO the event data to create (must not be {@code null})
     * @param userId the owner user identifier (must be positive)
     * @return the created event with generated identifier
     * @throws IllegalArgumentException if parameters are invalid or DTO is {@code null}
     * @throws EntityNotFoundException if user with specified ID doesn't exist
     */
    CalendarEventResponseDTO createEvent(CalendarEventRequestDTO calendarEventRequestDTO, int userId);

    /**
     * Updates an existing calendar event.
     *
     * @param calendarEventUpdateDTO the event data to update (must not be {@code null})
     * @param eventId the event identifier to update (must be positive)
     * @return the updated event data
     * @throws IllegalArgumentException if parameters are invalid or DTO is {@code null}
     * @throws EntityNotFoundException if event or user doesn't exist
     */
    CalendarEventResponseDTO updateEvent(CalendarEventUpdateDTO calendarEventUpdateDTO, int eventId);

    /**
     * Deletes a calendar event by its identifier.
     *
     * @param eventId the event identifier to delete (must be positive)
     * @throws IllegalArgumentException if eventId is not positive
     * @throws EntityNotFoundException if event doesn't exist
     */
    void deleteEvent(int eventId);
}
