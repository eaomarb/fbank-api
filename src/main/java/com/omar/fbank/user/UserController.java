package com.omar.fbank.user;

import com.omar.fbank.auth.AuthenticationResponse;
import com.omar.fbank.user.dto.UserRequestDto;
import com.omar.fbank.user.dto.UserResponseDto;
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
public class UserController {
    private final UserService service;

    @GetMapping("")
    @PreAuthorize("hasAuthority('ADMIN')")
    public Page<UserResponseDto> getUsers(Pageable pageable) {
        return service.getUsersDto(pageable);
    }

    @PostMapping("/{userId}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    @PreAuthorize("hasAuthority('ADMIN') or @authService.canModifyUser(#userId)")
    public AuthenticationResponse updateUser(@PathVariable("userId") UUID userId,
                                             @RequestBody UserRequestDto userRequestDto) {
        return service.updateUser(userId, userRequestDto);
    }


}