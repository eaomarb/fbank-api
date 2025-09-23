package com.omar.fbank.address;

import com.omar.fbank.address.dto.AddressRequestDto;
import com.omar.fbank.address.dto.AddressResponseDto;
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

import java.util.Optional;
import java.util.UUID;

@RestController
@RequestMapping("/api/addresses")
@RequiredArgsConstructor
@Tag(name = "Addresses", description = "Handle customer address details")
public class AddressController {
    private final AddressService service;

    @GetMapping("/{addressId}")
    @PreAuthorize("hasAuthority('ADMIN') or @authService.canAccessAddress(#addressId)")
    @Operation(summary = "Get address by ID", description = "Returns an address if the user has access or is an admin")
    public Optional<AddressResponseDto> getAddressById(@Parameter(description = "UUID of the address") @PathVariable UUID addressId) {
        return service.getAddressDtoById(addressId);
    }

    @GetMapping("")
    @PreAuthorize("hasAuthority('ADMIN')")
    @Operation(summary = "Get all addresses (admin only)", description = "Returns a list of addresses")
    public Page<AddressResponseDto> getAddresses(Pageable pageable) {
        return service.getAddressesDto(pageable);
    }

    @PutMapping("/{addressId}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    @PreAuthorize("hasAuthority('ADMIN') or @authService.canAccessAddress(#addressId)")
    @Operation(summary = "Update address", description = "Updates an existing address if the user has permission or is an admin")
    public void updateAddress(@Parameter(description = "UUID of the address") @PathVariable UUID addressId,
                              @Valid @RequestBody AddressRequestDto addressRequestDto) {
        service.updateAddress(addressId, addressRequestDto);
    }
}
