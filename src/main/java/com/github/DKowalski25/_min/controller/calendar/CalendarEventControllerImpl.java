package com.github.DKowalski25._min.controller.calendar;

import com.github.DKowalski25._min.dto.calendar.CalendarEventRequestDTO;
import com.github.DKowalski25._min.dto.calendar.CalendarEventResponseDTO;
import com.github.DKowalski25._min.dto.calendar.CalendarEventUpdateDTO;
import com.github.DKowalski25._min.models.config.CustomUserDetails;
import com.github.DKowalski25._min.service.calendar.CalendarEventService;

import lombok.RequiredArgsConstructor;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/calendar")
public class CalendarEventControllerImpl implements CalendarEventController {

    private final CalendarEventService calendarEventService;

    @Override
    @PostMapping
    public ResponseEntity<CalendarEventResponseDTO> createEvent(
            @AuthenticationPrincipal CustomUserDetails userDetails,
            @RequestBody CalendarEventRequestDTO calendarEventRequestDTO) {
        CalendarEventResponseDTO calendarEventResponseDTO = calendarEventService.createEvent(
                calendarEventRequestDTO, userDetails.getId());
        return ResponseEntity.status(HttpStatus.CREATED).body(calendarEventResponseDTO);
    }

    @Override
    @GetMapping
    public ResponseEntity<List<CalendarEventResponseDTO>> getUserEvents(@AuthenticationPrincipal CustomUserDetails userDetails) {
        List<CalendarEventResponseDTO> calendarEventResponseDTOs = calendarEventService.getUserEvents(userDetails.getId());
        return ResponseEntity.ok(calendarEventResponseDTOs);
    }

    @Override
    @PatchMapping("/update/{eventId}")
    public ResponseEntity<CalendarEventResponseDTO> updateCalendarEvent(
            @AuthenticationPrincipal CustomUserDetails userDetails,
            @RequestBody CalendarEventUpdateDTO calendarEventUpdateDTO,
            @PathVariable UUID eventId) {
        CalendarEventResponseDTO calendarEventResponseDTO = calendarEventService.updateEvent(
                calendarEventUpdateDTO, eventId, userDetails.getId());
        return ResponseEntity.ok(calendarEventResponseDTO);
    }

    @Override
    @DeleteMapping("/{eventId}")
    public ResponseEntity<Void> deleteCalendarEvent(
            @AuthenticationPrincipal CustomUserDetails userDetails, @PathVariable UUID eventId) {
        calendarEventService.deleteEvent(eventId, userDetails.getId());
        return ResponseEntity.noContent().build();
    }
}
