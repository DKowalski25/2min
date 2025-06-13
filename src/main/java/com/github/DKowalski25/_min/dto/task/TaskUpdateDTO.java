package com.github.DKowalski25._min.dto.task;

import com.github.DKowalski25._min.models.Tag;

public record TaskUpdateDTO(
        String title,
        String description,
        Tag tag,
        Boolean isDone,
        Integer timeBlockId
) {}
