package com.github.DKowalski25._min.service.calendar;

import com.github.DKowalski25._min.dto.calendar.CalendarEventMapper;
import com.github.DKowalski25._min.dto.calendar.CalendarEventRequestDTO;
import com.github.DKowalski25._min.dto.calendar.CalendarEventResponseDTO;
import com.github.DKowalski25._min.dto.calendar.CalendarEventUpdateDTO;
import com.github.DKowalski25._min.exceptions.AccessDeniedException;
import com.github.DKowalski25._min.exceptions.EntityNotFoundException;
import com.github.DKowalski25._min.models.CalendarEvent;
import com.github.DKowalski25._min.models.User;
import com.github.DKowalski25._min.repository.calendar.CalendarEventRepository;
import com.github.DKowalski25._min.repository.user.UserRepository;

import lombok.RequiredArgsConstructor;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class CalendarEventServiceImpl implements CalendarEventService {
    private final CalendarEventRepository calendarEventRepository;
    private final UserRepository userRepository;
    private final CalendarEventMapper calendarEventMapper;

    @Override
    @Transactional(readOnly = true)
    public List<CalendarEventResponseDTO> getUserEvents(UUID userId) {
        return calendarEventRepository.findByUserId(userId).stream()
                .map(calendarEventMapper::toResponse)
                .collect(Collectors.toList());
    }

    @Override
    @Transactional
    public CalendarEventResponseDTO createEvent(CalendarEventRequestDTO calendarEventRequestDTO, UUID userId) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new EntityNotFoundException("User not found"));

        CalendarEvent calendarEvent = calendarEventMapper.toEntity(calendarEventRequestDTO);
        calendarEvent.setUser(user);
        calendarEventRepository.save(calendarEvent);

        return calendarEventMapper.toResponse(calendarEvent);
    }

    @Override
    @Transactional
    public CalendarEventResponseDTO updateEvent(CalendarEventUpdateDTO calendarEventUpdateDTO, UUID eventId, UUID userId) {
        CalendarEvent existingCalendarEvent = calendarEventRepository.findById(eventId)
                .orElseThrow(() -> new EntityNotFoundException("Calendar event not found", eventId));

        if (!existingCalendarEvent.getUser().getId().equals(userId)) {
            throw new AccessDeniedException("You don't have permission to update this event");
        }

        calendarEventMapper.updateEventFromDto(calendarEventUpdateDTO, existingCalendarEvent);
        calendarEventRepository.save(existingCalendarEvent);
        return calendarEventMapper.toResponse(existingCalendarEvent);
    }

    @Override
    @Transactional
    public void deleteEvent(UUID eventId, UUID userId) {
        CalendarEvent event = calendarEventRepository.findById(eventId)
                .orElseThrow(() -> new EntityNotFoundException("Event not found"));

        if (!event.getUser().getId().equals(userId)) {
            throw new AccessDeniedException("You don't have permission to update this event");
        }
        calendarEventRepository.deleteEventById(eventId);
    }
}
