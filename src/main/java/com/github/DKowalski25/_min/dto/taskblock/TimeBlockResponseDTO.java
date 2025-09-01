package com.github.DKowalski25._min.dto.taskblock;

import com.github.DKowalski25._min.models.TimeType;

import java.util.UUID;

public record TimeBlockResponseDTO(UUID id, TimeType type) {}
