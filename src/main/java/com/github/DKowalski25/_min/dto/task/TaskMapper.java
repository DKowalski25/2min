package com.github.DKowalski25._min.dto.task;

import com.github.DKowalski25._min.dto.taskblock.TimeBlockResponseDTO;
import com.github.DKowalski25._min.models.Task;
import com.github.DKowalski25._min.models.TimeBlock;

import com.github.DKowalski25._min.models.TimeType;
import org.mapstruct.*;

@Mapper(componentModel = "spring")
public interface TaskMapper {


    @Mapping(target = "id", ignore = true)
    @Mapping(target = "done", constant = "false")
    @Mapping(target = "timeBlockType", source = "timeBlock")
    Task toEntity(TaskRequestDTO dto);

    @Mapping(target = "timeBlockType", source = "timeBlock.type")
    TaskResponseDTO toResponse(Task task);
//
//    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
//    @Mapping(target = "timeBlockType", source = "timeBlockType")
//    void updateFromDto(TaskUpdateDTO dto, @MappingTarget Task task);
}