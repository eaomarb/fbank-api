package com.omar.fbank.user.dto;

import com.omar.fbank.user.User;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.time.ZoneId;

@Component
@RequiredArgsConstructor
public class UserDtoMapper {
    public UserResponseDto toResponseDto(User user) {
        return new UserResponseDto(
                user.getId(),
                user.getEmail(),
                user.getDisplayName(),
                user.getRole(),
                LocalDateTime.ofInstant(user.getCreatedAt(), ZoneId.systemDefault())
        );
    }
}
