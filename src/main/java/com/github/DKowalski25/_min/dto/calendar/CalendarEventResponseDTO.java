package com.github.DKowalski25._min.dto.calendar;

import com.github.DKowalski25._min.models.User;

public record CalendarEventResponseDTO(
    int id,
    String title,
    String description,
    String startEvent,
    String endEvent,
    User organizer
) {}
