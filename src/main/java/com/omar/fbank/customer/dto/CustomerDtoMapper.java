package com.omar.fbank.customer.dto;

import com.omar.fbank.address.dto.AddressDtoMapper;
import com.omar.fbank.customer.Customer;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class CustomerDtoMapper {
    private final AddressDtoMapper addressDtoMapper;

    public Customer toEntity(CustomerRequestDto customerRequestDto) {
        Customer customer = new Customer();

        customer.setName(customerRequestDto.name());
        customer.setDocumentId(customerRequestDto.documentId());
        customer.setEmail(customerRequestDto.email());
        customer.setLastName(customerRequestDto.lastName());
        customer.setEmail(customerRequestDto.email());
        customer.setAddress(addressDtoMapper.toEntity(customerRequestDto.address()));
        customer.setAge(customerRequestDto.age());
        customer.setPhone(customerRequestDto.phone());

        return customer;
    }

    public CustomerResponseDto toResponseDto(Customer customer) {
        return new CustomerResponseDto(
                customer.getId(),
                customer.getName(),
                customer.getLastName(),
                customer.getDocumentId(),
                addressDtoMapper.toResponseDto(customer.getAddress()),
                customer.getAge(),
                customer.getEmail(),
                customer.getPhone()
        );
    }
}
