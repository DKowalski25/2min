package com.github.DKowalski25._min.service.calendar;

import com.github.DKowalski25._min.dto.calendar.CalendarEventMapper;
import com.github.DKowalski25._min.dto.calendar.CalendarEventRequestDTO;
import com.github.DKowalski25._min.dto.calendar.CalendarEventResponseDTO;
import com.github.DKowalski25._min.dto.calendar.CalendarEventUpdateDTO;
import com.github.DKowalski25._min.exceptions.EntityNotFoundException;
import com.github.DKowalski25._min.models.CalendarEvent;
import com.github.DKowalski25._min.models.User;
import com.github.DKowalski25._min.repository.calendar.CalendarEventRepository;
import com.github.DKowalski25._min.repository.user.UserRepository;

import lombok.RequiredArgsConstructor;

import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class CalendarEventServiceImpl implements CalendarEventService {
    private final CalendarEventRepository calendarEventRepository;
    private final UserRepository userRepository;
    private final CalendarEventMapper calendarEventMapper;

    @Override
    public List<CalendarEventResponseDTO> getUserEvents(int userId) {
        return calendarEventRepository.findByUserId(userId).stream()
                .map(calendarEventMapper::toResponse)
                .collect(Collectors.toList());
    }

    @Override
    public CalendarEventResponseDTO createEvent(CalendarEventRequestDTO calendarEventRequestDTO, int userId) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new EntityNotFoundException("User not found"));

        CalendarEvent calendarEvent = calendarEventMapper.toEntity(calendarEventRequestDTO);
        calendarEvent.setUser(user);
        calendarEventRepository.save(calendarEvent);

        return calendarEventMapper.toResponse(calendarEvent);
    }

    @Override
    public CalendarEventResponseDTO updateEvent(CalendarEventUpdateDTO calendarEventUpdateDTO, int eventId) {
        CalendarEvent existingCalendarEvent = calendarEventRepository.findById(eventId)
                .orElseThrow(() -> new EntityNotFoundException("Calendar event not found", eventId));

        calendarEventMapper.updateEventFromDto(calendarEventUpdateDTO, existingCalendarEvent);
        calendarEventRepository.save(existingCalendarEvent);
        return calendarEventMapper.toResponse(existingCalendarEvent);
    }

    @Override
    public void deleteEvent(int eventId) {
        calendarEventRepository.deleteEventByEventId(eventId);
    }
}
