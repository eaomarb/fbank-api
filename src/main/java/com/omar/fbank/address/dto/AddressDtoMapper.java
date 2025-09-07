package com.omar.fbank.address.dto;

import com.omar.fbank.address.Address;
import org.springframework.stereotype.Component;

@Component
public class AddressDtoMapper {
    public Address toEntity(AddressRequestDto addressRequestDto) {
        Address address = new Address();

        address.setCity(addressRequestDto.city());
        address.setDoor(addressRequestDto.door());
        address.setFloor(addressRequestDto.floor());
        address.setStreetNumber(addressRequestDto.streetNumber());
        address.setStreetName(addressRequestDto.streetName());
        address.setProvince(addressRequestDto.province());
        address.setPostalCode(addressRequestDto.postalCode());

        return address;
    }

    public AddressResponseDto toResponseDto(Address address) {
        return new AddressResponseDto(
                address.getId(),
                address.getStreetName(),
                address.getStreetNumber(),
                address.getFloor(),
                address.getDoor(),
                address.getPostalCode(),
                address.getCity(),
                address.getProvince()
        );
    }

}
