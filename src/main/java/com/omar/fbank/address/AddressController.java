package com.omar.fbank.address;

import com.omar.fbank.address.dto.AddressRequestDto;
import com.omar.fbank.address.dto.AddressResponseDto;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;
import java.util.UUID;

@RestController
@RequestMapping("/api/addresses")
@RequiredArgsConstructor
public class AddressController {
    private final AddressService service;

    @GetMapping("/{addressId}")
    @PreAuthorize("hasAuthority('ADMIN') or @authService.canAccessAddress(#addressId)")
    public Optional<AddressResponseDto> getAddressById(@PathVariable UUID addressId) {
        return service.getAddressDtoById(addressId);
    }

    @GetMapping("")
    @PreAuthorize("hasAuthority('ADMIN')")
    public Page<AddressResponseDto> getAddresses(Pageable pageable) {
        return service.getAddressesDto(pageable);
    }

    @PutMapping("/{addressId}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    @PreAuthorize("hasAuthority('ADMIN') or @authService.canAccessAddress(#addressId)")
    public void updateAddress(@PathVariable UUID addressId,
                              @Valid @RequestBody AddressRequestDto addressRequestDto) {
        service.updateAddress(addressId, addressRequestDto);
    }
}
