package com.omar.fbank.customer.dto;

import com.omar.fbank.address.dto.AddressDtoMapper;
import com.omar.fbank.customer.Customer;
import com.omar.fbank.user.User;
import com.omar.fbank.user.UserService;
import com.omar.fbank.user.dto.UserDtoMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.UUID;

@Component
@RequiredArgsConstructor
public class CustomerDtoMapper {
    private final AddressDtoMapper addressDtoMapper;
    private final UserDtoMapper userDtoMapper;
    private final UserService userService;

    public Customer toEntity(CustomerRequestDto customerRequestDto, UUID userId) {
        Customer customer = new Customer();

        User user = userService.getUserById(userId);

        customer.setName(customerRequestDto.name());
        customer.setDocumentId(customerRequestDto.documentId());
        customer.setLastName(customerRequestDto.lastName());
        customer.setAddress(addressDtoMapper.toEntity(customerRequestDto.address()));
        customer.setAge(customerRequestDto.age());
        customer.setPhone(customerRequestDto.phone());
        customer.setUser(user);


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
                customer.getPhone(),
                userDtoMapper.toResponseDto(customer.getUser())
        );
    }
}
