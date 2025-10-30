package com.omar.fbank.user;

import com.omar.fbank.auth.AuthenticationResponse;
import com.omar.fbank.user.dto.UserRequestDto;
import com.omar.fbank.user.dto.UserResponseDto;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
@RequestMapping("/api/users")
@RequiredArgsConstructor
@Tag(name = "Users", description = "View all users and update user information")
public class UserController {
    private final UserService service;

    @GetMapping("")
    @PreAuthorize("hasAuthority('ADMIN')")
    @Operation(summary = "List all users", description = "Returns a list of all registered users. Only admin can access.")
    public Page<UserResponseDto> getUsers(Pageable pageable) {
        return service.getUsersDto(pageable);
    }

    @PostMapping("/{userId}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    @PreAuthorize("hasAuthority('ADMIN') or @authService.canAccessUser(#userId)")
    @Operation(summary = "Update user information", description = "Updates an existing user's email, display name, and password. Accessible to admin or the user.")
    public AuthenticationResponse updateUser(@Parameter(description = "UUID of the user to update") @PathVariable("userId") UUID userId,
                                             @Valid @RequestBody UserRequestDto userRequestDto) {
        return service.updateUser(userId, userRequestDto);
    }
}