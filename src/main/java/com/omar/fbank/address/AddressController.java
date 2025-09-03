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

    @GetMapping("/{id}")
    public Optional<Address> getAddress(@PathVariable UUID id) {
        return service.findById(id);
    }

    @GetMapping("")
    public List<Address> getAddresses() {
        return service.findAll();
    }

    @PutMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void updateAddress(@PathVariable UUID id, @RequestBody @Valid Address address) {
        service.updateAddress(id, address);
    }
}
