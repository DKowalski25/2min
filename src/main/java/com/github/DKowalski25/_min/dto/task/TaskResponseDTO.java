package com.github.DKowalski25._min.dto.task;

import com.github.DKowalski25._min.models.Tag;
import com.github.DKowalski25._min.models.TimeType;

import java.time.LocalDateTime;
import java.util.UUID;

public record TaskResponseDTO(
        UUID id,
        String title,
        String description,
        Tag tag,
        boolean done,
        boolean plannedForNext,
        LocalDateTime archivedAt,
        LocalDateTime createdAt,
        String periodMarker,
        TimeType timeBlockType,
        UUID userId
) {}
