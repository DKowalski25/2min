package com.github.DKowalski25._min.dto.user;

import com.github.DKowalski25._min.models.User;

import org.mapstruct.*;

@Mapper(componentModel = "spring")
public interface UserMapper {

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "role", constant = "USER") // или default значение
    @Mapping(target = "blocked", constant = "false")
    @Mapping(target = "tasks", ignore = true)
    @Mapping(target = "timeBlocks", ignore = true)
    User toEntity(UserRequestDTO dto);

    @Mapping(target = "password", ignore = true)
    @Mapping(target = "tasks", ignore = true)
    @Mapping(target = "timeBlocks", ignore = true)
    User toEntity(UserResponseDTO dto);

    UserResponseDTO toResponse(User user);

    @Mapping(target = "password", ignore = true)
    UserRequestDTO toRequest(User user);

    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    @Mapping(target = "id", ignore = true)
    @Mapping(target = "tasks", ignore = true)
    @Mapping(target = "timeBlocks", ignore = true)
    void updateUserFromDto(UserUpdateDTO dto, @MappingTarget User user);
}
