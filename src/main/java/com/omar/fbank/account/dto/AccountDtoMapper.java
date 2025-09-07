package com.omar.fbank.account.dto;

import com.omar.fbank.account.Account;
import org.springframework.stereotype.Component;

@Component
public class AccountDtoMapper {
    public AccountResponseDto toResponseDto(Account account) {
        return new AccountResponseDto(
                account.getId(),
                account.getBalance(),
                account.getIban(),
                account.getStatus()
        );
    }
}
