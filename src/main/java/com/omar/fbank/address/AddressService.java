package com.omar.fbank.address;

import com.omar.fbank.address.dto.AddressDtoMapper;
import com.omar.fbank.address.dto.AddressRequestDto;
import com.omar.fbank.address.dto.AddressResponseDto;
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
    private final AddressDtoMapper mapper;

    public Optional<AddressResponseDto> getAddressDtoById(UUID addressId) {
        return Optional.ofNullable(mapper.toResponseDto(repository.findById(addressId)
                .orElseThrow(AddressNotFoundException::new)));
    }

    public List<AddressResponseDto> getAddressesDto() {
        return repository.findAll()
                .stream()
                .map(mapper::toResponseDto)
                .toList();
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

            repository.save(updatedAddress);
        } else {
            throw new AddressNotFoundException();
        }
    }
}
