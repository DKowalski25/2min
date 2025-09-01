package com.github.DKowalski25._min.dto.user;

import com.github.DKowalski25._min.models.HistoryRetention;
import jakarta.validation.constraints.Size;

public record UserUpdateDTO (
        @Size(min = 3, max = 50, message = "Username must be between 3 and 50 characters")
        String username,

        @Size(min = 3, max = 50, message = "Password must be between 3 and 50 characters")
        String password,

        @Size(min = 3, max = 50, message = "Email must be between 3 and 50 characters")
        String email,

        Boolean blocked,

        HistoryRetention historyRetention
)
{}
