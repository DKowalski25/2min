package com.github.DKowalski25._min.dto.calendar;

import com.github.DKowalski25._min.dto.user.UserResponseDTO;

import java.util.UUID;

public record CalendarEventResponseDTO(
    UUID id,
    String title,
    String description,
    String startEvent,
    String endEvent,
    UserResponseDTO organizer
) {}
