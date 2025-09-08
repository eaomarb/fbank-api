package com.omar.fbank.customeraccount.dto;

import com.omar.fbank.account.dto.AccountDtoMapper;
import com.omar.fbank.customer.dto.CustomerDtoMapper;
import com.omar.fbank.customeraccount.CustomerAccount;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@RequiredArgsConstructor
@Component
public class CustomerAccountDtoMapper {
    private final CustomerDtoMapper customerDtoMapper;
    private final AccountDtoMapper accountDtoMapper;

    public CustomerAccountResponseDto toResponseDto(CustomerAccount customerAccount) {
        return new CustomerAccountResponseDto(
                customerAccount.getId(),
                customerDtoMapper.toResponseDto(customerAccount.getCustomer()).id(),
                accountDtoMapper.toResponseDto(customerAccount.getAccount()).id(),
                customerAccount.isOwner()
        );
    }
}
