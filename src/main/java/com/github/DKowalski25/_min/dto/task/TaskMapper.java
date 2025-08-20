package com.github.DKowalski25._min.dto.task;

import com.github.DKowalski25._min.models.Task;

import com.github.DKowalski25._min.models.TimeBlock;
import com.github.DKowalski25._min.models.User;

import org.mapstruct.*;

import java.util.UUID;

@Mapper(componentModel = "spring",
        nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
public interface TaskMapper {

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "done", constant = "false")
    @Mapping(target = "timeBlock", source = "dto.timeBlockId", qualifiedByName = "mapTimeBlock")
    @Mapping(target = "user", source = "userId", qualifiedByName = "mapUser")
    Task toEntity(TaskRequestDTO dto, UUID userId);

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "done", constant = "false")
    @Mapping(target = "timeBlock", source = "dto.timeBlockId", qualifiedByName = "mapTimeBlock")
    @Mapping(target = "user", source = "userId", qualifiedByName = "mapUser")
    Task toEntityWithUser(TaskRequestDTO dto, UUID userId);

    @Mapping(target = "timeBlockType", source = "timeBlock.type")
    @Mapping(target = "userId", source = "user.id")
    TaskResponseDTO toResponse(Task task);

    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    @Mapping(target = "id", ignore = true)
    @Mapping(target = "timeBlock", ignore = true)
    @Mapping(target = "user", ignore = true)
    void updateFromDto(TaskUpdateDTO dto, @MappingTarget Task task);


    @Named("mapUser")
    default User mapUser(UUID userId) {
        User user = new User();
        user.setId(userId);
        return user;
    }

    @Named("mapTimeBlock")
    default TimeBlock mapTimeBlock(UUID timeBlockId) {
        TimeBlock block = new TimeBlock();
        block.setId(timeBlockId);
        return block;
    }
}
