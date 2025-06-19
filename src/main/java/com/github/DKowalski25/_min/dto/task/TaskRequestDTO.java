package com.github.DKowalski25._min.dto.task;

import com.github.DKowalski25._min.models.Tag;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.Size;


public record TaskRequestDTO(
    @NotBlank(message = "Title cannot be blank")
    @Size(min = 1, max = 50, message = "Title must be between 1 and 50 characters")
    String title,

    @NotBlank(message = "Description cannot be blank")
    @Size(min = 1, max = 1000, message = "Description must be between 1 and 1000 characters")
    String description,

    Tag tag,

    @NotNull
    @Positive
    Integer timeBlockId,

    @NotNull
    @Positive
    Integer userId
) {}
