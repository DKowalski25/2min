package com.github.DKowalski25._min.dto.user;

import com.github.DKowalski25._min.models.UserRole;

import java.util.UUID;

public record UserResponseDTO (
    UUID id,
    String username,
    String email,
    UserRole role,
    boolean blocked
) {}
