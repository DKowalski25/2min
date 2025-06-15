package com.github.DKowalski25._min.dto.user;

import com.github.DKowalski25._min.models.User;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface UserMapper {

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "role", constant = "USER") // или default значение
    @Mapping(target = "blocked", constant = "false")
    User toEntity(UserRequestDTO dto);

    UserResponseDTO toResponse(User user);

    @Mapping(target = "password", ignore = true)
    UserRequestDTO toRequest(User user);
}
