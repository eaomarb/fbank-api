package com.omar.fbank.address;

import com.omar.fbank.address.dto.AddressDtoMapper;
import com.omar.fbank.address.dto.AddressRequestDto;
import com.omar.fbank.address.dto.AddressResponseDto;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.Optional;
import java.util.UUID;

@Service
@RequiredArgsConstructor
@Transactional
public class AddressService {
    private final AddressRepository repository;
    private final AddressDtoMapper mapper;

    public Optional<AddressResponseDto> getAddressDtoById(UUID addressId) {
        return Optional.ofNullable(mapper.toResponseDto(repository.findById(addressId)
                .orElseThrow(AddressNotFoundException::new)));
    }

    public Page<AddressResponseDto> getAddressesDto(Pageable pageable) {
        return repository
                .findAll(pageable)
                .map(mapper::toResponseDto);
    }

    public void updateAddress(UUID addressId, AddressRequestDto addressRequestDto) {
        Optional<Address> addressOptional = repository.findById(addressId);

        if (addressOptional.isPresent()) {
            Address updatedAddress = addressOptional.get();

            updatedAddress.setCity(addressRequestDto.city());
            updatedAddress.setDoor(addressRequestDto.door());
            updatedAddress.setFloor(addressRequestDto.floor());
            updatedAddress.setProvince(addressRequestDto.province());
            updatedAddress.setStreetNumber(addressRequestDto.streetNumber());
            updatedAddress.setStreetName(addressRequestDto.streetName());
            updatedAddress.setPostalCode(addressRequestDto.postalCode());

            repository.saveAndFlush(updatedAddress);
        } else {
            throw new AddressNotFoundException();
        }
    }

    public void reactivateAddress(UUID addressId) {
        repository.reactivateCustomerById(addressId);
    }
}
