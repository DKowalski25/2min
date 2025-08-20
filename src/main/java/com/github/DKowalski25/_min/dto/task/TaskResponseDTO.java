package com.github.DKowalski25._min.dto.task;

import com.github.DKowalski25._min.models.Tag;
import com.github.DKowalski25._min.models.TimeType;

import java.util.UUID;

public record TaskResponseDTO(
        UUID id,
        String title,
        String description,
        Tag tag,
        boolean done,
        TimeType timeBlockType,
        UUID userId
) {}
