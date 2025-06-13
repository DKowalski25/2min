package com.github.DKowalski25._min.dto.task;

import com.github.DKowalski25._min.models.Tag;

public record TaskResponseDTO(
        int id,
        String title,
        String description,
        Tag tag,
        boolean isDone,
        Integer timeBlock
) {}
