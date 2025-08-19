package com.github.DKowalski25._min.dto.calendar;

import com.github.DKowalski25._min.models.CalendarEvent;

import org.mapstruct.*;

import javax.swing.*;

@Mapper(componentModel = "spring")
public interface CalendarEventMapper {
    @Mapping(target = "id", ignore = true)
    @Mapping(target = "user", ignore = true)
    @Mapping(target = "startEvent", expression = "java(LocalDateTime.parse(dto.start_event()))")
    @Mapping(target = "endEvent", expression = "java(LocalDateTime.parse(dto.end_event()))")
    CalendarEvent toEntity(CalendarEventRequestDTO dto);

    CalendarEventResponseDTO toResponse(CalendarEvent calendarEvent);

    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    void updateEventFromDto(CalendarEventUpdateDTO dto, @MappingTarget CalendarEvent calendarEvent);
}
