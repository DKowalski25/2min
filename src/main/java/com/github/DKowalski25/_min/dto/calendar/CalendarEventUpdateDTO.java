package com.github.DKowalski25._min.dto.calendar;

import jakarta.validation.constraints.Size;

public record CalendarEventUpdateDTO(
        @Size(max = 100, message = "Title cannot exceed 100 characters")
        String title,

        @Size(max = 500, message = "Description cannot exceed 500 characters")
        String description,

        String startEvent,

        String endEvent
) {}
