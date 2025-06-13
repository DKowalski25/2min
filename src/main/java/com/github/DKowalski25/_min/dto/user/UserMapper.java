package com.github.DKowalski25._min.dto.user;

import com.github.DKowalski25._min.models.User;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface UserMapper {
    User toEntity(UserRequestDTO userDTO);

    @Mapping(target = "isBlocked", source = "blocked")
    UserResponseDTO toResponse(User user);

    @Mapping(target = "password", ignore = true)
    UserRequestDTO toRequest(User user);
}
