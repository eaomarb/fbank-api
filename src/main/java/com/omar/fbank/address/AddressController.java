package com.omar.fbank.address;

import com.omar.fbank.address.dto.AddressRequestDto;
import com.omar.fbank.address.dto.AddressResponseDto;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@RestController
@RequestMapping("/api/addresses")
@RequiredArgsConstructor
public class AddressController {
    private final AddressService service;

    @GetMapping("/{addressId}")
    public Optional<AddressResponseDto> getAddressById(@PathVariable UUID addressId) {
        return service.getAddressDtoById(addressId);
    }

    @GetMapping("")
    public List<AddressResponseDto> getAddresses() {
        return service.getAddressesDto();
    }

    @PutMapping("/{addressId}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void updateAddress(@PathVariable UUID addressId, @Valid @RequestBody AddressRequestDto addressRequestDto) {
        service.updateAddress(addressId, addressRequestDto);
    }
}
