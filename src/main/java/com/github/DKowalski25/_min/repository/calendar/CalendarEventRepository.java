package com.github.DKowalski25._min.repository.calendar;

import com.github.DKowalski25._min.controller.calendar.CalendarEventController;
import com.github.DKowalski25._min.models.CalendarEvent;
import com.github.DKowalski25._min.service.calendar.CalendarEventService;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

/**
 * Spring Data JPA repository for {@link CalendarEvent} entities management.
 *
 * <p><strong>Note:</strong> This interface relies on Spring Data JPA capabilities.
 * All query methods return collections or primitive results where appropriate.
 * Business exception handling should be performed at the service layer.</p>
 *
 * <p><strong>Usage example:</strong>
 * <pre>{@code
 * // In service layer:
 * List<CalendarEvent> events = calendarEventRepository.findByUserId(userId);
 * }</pre></p>
 *
 * @see CalendarEventService
 * @see CalendarEventController
 */
public interface CalendarEventRepository extends JpaRepository<CalendarEvent, Integer> {
    /**
     * Finds all calendar events associated with the specified user ID.
     *
     * @param userId the ID of the user to search events for (must be positive)
     * @return a {@link List} of found calendar events, possibly empty
     */
    List<CalendarEvent> findByUserId(int userId);

    /**
     * Deletes a calendar event by its ID.
     *
     * @param id the ID of the event to delete (must be positive)
     * @throws org.springframework.dao.EmptyResultDataAccessException if no event exists with the given ID
     */
    void deleteEventById(int id);
}
