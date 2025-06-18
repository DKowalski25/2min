package com.github.DKowalski25._min.dto.task;

import com.github.DKowalski25._min.models.Tag;
import com.github.DKowalski25._min.models.TimeType;

public record TaskRequestDTO(
    String title,
    String description,
    Tag tag,
    Integer timeBlockId
) {}
