package com.omar.fbank.user.dto;

import com.omar.fbank.user.UserRole;

import java.time.LocalDateTime;
import java.util.UUID;

public record UserResponseDto(
        UUID id,
        String email,
        String displayName,
        UserRole role,
        LocalDateTime createdAt
) {
}
