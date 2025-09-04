package com.omar.fbank.address;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;

import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
@RequiredArgsConstructor
@Transactional
public class AddressService {
    private final AddressRepository repository;

    public Optional<Address> getAddressById(UUID addressId) {
        return Optional.ofNullable(repository.findById(addressId)
                .orElseThrow(AddressNotFoundException::new));
    }

    public List<Address> getAddresses() {
        return repository.findAll();
    }

    public void updateAddress(UUID addressId, Address address) {
        Optional<Address> addressOptional = repository.findById(addressId);

        if (addressOptional.isPresent()) {
            Address updatedAddress = addressOptional.get();

            updatedAddress.setCity(address.getCity());
            updatedAddress.setDoor(address.getDoor());
            updatedAddress.setFloor(address.getFloor());
            updatedAddress.setProvince(address.getProvince());
            updatedAddress.setStreetNumber(address.getStreetNumber());
            updatedAddress.setStreetName(address.getStreetName());

            repository.save(updatedAddress);
        } else {
            throw new AddressNotFoundException();
        }
    }
}
