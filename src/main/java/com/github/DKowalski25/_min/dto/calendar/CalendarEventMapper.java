package com.github.DKowalski25._min.dto.calendar;

import com.github.DKowalski25._min.models.CalendarEvent;

import org.mapstruct.BeanMapping;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;
import org.mapstruct.NullValuePropertyMappingStrategy;

public interface CalendarEventMapper {
    @Mapping(target = "id", ignore = true)
    @Mapping(target = "user", ignore = true)
    CalendarEvent toEntity(CalendarEventRequestDTO dto);

    CalendarEventResponseDTO toResponse(CalendarEvent calendarEvent);

    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    void updateEventFromDto(CalendarEventUpdateDTO dto, @MappingTarget CalendarEvent calendarEvent);
}
