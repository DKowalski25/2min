package com.github.DKowalski25._min.dto.user;

import com.github.DKowalski25._min.models.UserRole;

public record UserResponseDTO (
    Integer id,
    String username,
    String email,
    UserRole role,
    boolean blocked
) {}
