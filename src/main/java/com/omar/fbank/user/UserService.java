package com.omar.fbank.user;

import com.omar.fbank.auth.AuthenticationResponse;
import com.omar.fbank.config.JwtService;
import com.omar.fbank.user.dto.UserDtoMapper;
import com.omar.fbank.user.dto.UserRequestDto;
import com.omar.fbank.user.dto.UserResponseDto;
import com.omar.fbank.user.exception.EmailAlreadyExistsException;
import com.omar.fbank.user.exception.UserNotFoundException;
import jakarta.transaction.Transactional;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
@Transactional
@RequiredArgsConstructor
public class UserService {
    private final UserRepository repository;
    private final PasswordEncoder passwordEncoder;
    private final JwtService jwtService;
    private final UserDtoMapper mapper;

    public AuthenticationResponse updateUser(UUID userId, @Valid UserRequestDto userRequestDto) {
        User user = repository.findById(userId).orElseThrow(UserNotFoundException::new);

        if (repository.existsByEmailAndIdNot(userRequestDto.email(), userId)) {
            throw new EmailAlreadyExistsException();
        }
        user.setEmail(userRequestDto.email());
        user.setDisplayName(userRequestDto.displayName());
        user.setPassword(passwordEncoder.encode(userRequestDto.password()));

        repository.save(user);
        var jwtToken = jwtService.generateToken(user);
        return AuthenticationResponse.builder()
                .token(jwtToken)
                .build();
    }

    public Page<UserResponseDto> getUsersDto(Pageable pageable) {
        return repository
                .findAll(pageable)
                .map(mapper::toResponseDto);
    }

    public User getUserById(UUID userId) {
        return repository.findById(userId).orElseThrow(UserNotFoundException::new);
    }
}
