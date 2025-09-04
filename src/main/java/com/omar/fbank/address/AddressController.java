package com.omar.fbank.address;

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
    public Optional<Address> getAddressById(@PathVariable UUID addressId) {
        return service.getAddressById(addressId);
    }

    @GetMapping("")
    public List<Address> getAddresses() {
        return service.getAddresses();
    }

    @PutMapping("/{addressId}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void updateAddress(@PathVariable UUID addressId, @RequestBody @Valid Address address) {
        service.updateAddress(addressId, address);
    }
}
