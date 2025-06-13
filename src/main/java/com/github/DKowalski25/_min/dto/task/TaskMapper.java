package com.github.DKowalski25._min.dto.task;

import com.github.DKowalski25._min.models.Task;
import com.github.DKowalski25._min.models.TimeBlock;

import org.mapstruct.*;

@Mapper(componentModel = "spring")
public interface TaskMapper {

    Task toEntity(TaskRequestDTO taskRequestDTO);

    TaskResponseDTO toResponse(Task task);

    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    @Mapping(target = "timeBlock", source = "timeBlockId", qualifiedByName = "mapToTimeBlock")
    void updateTaskFromDto(TaskUpdateDTO dto, @MappingTarget Task task);

    @Named("mapToTimeBlock")
    default TimeBlock mapToTimeBlock(Integer timeBlockId) {
        if (timeBlockId == null) return null;
        TimeBlock block = new TimeBlock();
        block.setId(timeBlockId);
        return block;
    }
}
