package com.github.DKowalski25._min.dto.task;

import com.github.DKowalski25._min.models.Task;


import com.github.DKowalski25._min.models.TimeBlock;
import org.mapstruct.*;

@Mapper(componentModel = "spring")
public interface TaskMapper {

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "done", constant = "false")
    @Mapping(target = "timeBlock", source = "timeBlockId", qualifiedByName = "mapTimeBlock")
    Task toEntity(TaskRequestDTO dto);

    @Named("mapTimeBlock")
    default TimeBlock mapTimeBlock(int timeBlockId) {
        TimeBlock timeBlock = new TimeBlock();
        timeBlock.setId(timeBlockId);
        return timeBlock;
    }

    @Mapping(target = "timeBlockType", source = "timeBlock.type")
    TaskResponseDTO toResponse(Task task);
//
//    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
//    @Mapping(target = "timeBlockType", source = "timeBlockType")
//    void updateFromDto(TaskUpdateDTO dto, @MappingTarget Task task);
}