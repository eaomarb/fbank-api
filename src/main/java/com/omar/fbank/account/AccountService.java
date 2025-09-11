package com.omar.fbank.account;

import com.omar.fbank.account.dto.AccountDtoMapper;
import com.omar.fbank.account.dto.AccountResponseDto;
import com.omar.fbank.account.exception.AccountNotFoundException;
import com.omar.fbank.customer.Customer;
import com.omar.fbank.customer.CustomerRepository;
import com.omar.fbank.customer.exception.CustomerNotFoundException;
import com.omar.fbank.customeraccount.CustomerAccountService;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.iban4j.CountryCode;
import org.iban4j.Iban;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.Optional;
import java.util.UUID;

@Service
@RequiredArgsConstructor
@Transactional
public class AccountService {
    private final AccountRepository repository;
    private final CustomerRepository customerRepository;
    private final CustomerAccountService customerAccountService;
    private final AccountDtoMapper mapper;


    public Optional<AccountResponseDto> getAccountDtoById(UUID accountId) {
        return Optional.ofNullable(mapper.toResponseDto(repository.findById(accountId)
                .orElseThrow(AccountNotFoundException::new)));
    }

    public Optional<Account> getAccountById(UUID accountId) {
        return repository.findById(accountId);
    }

    public Page<AccountResponseDto> getAccountsDto(Pageable pageable) {
        return repository
                .findAll(pageable)
                .map(mapper::toResponseDto);
    }

    public AccountResponseDto createAccount(UUID customerId, Pageable pageable) {
        Account account = new Account();
        Customer customer = customerRepository.getCustomerById(customerId);

        if (customer == null) {
            throw new CustomerNotFoundException();
        }

        String iban;
        do {
            iban = Iban.random(CountryCode.ES).toString();
        } while (repository.existsByIban(iban));

        account.setIban(iban);
        account.setBalance(BigDecimal.valueOf(0));
        account.setStatus(AccountStatus.ACTIVE);
        repository.save(account);

        customerAccountService.create(customer, account, pageable);

        return mapper.toResponseDto(account);
    }

    public void deleteAccount(UUID accountId) {
        if (!repository.existsById(accountId)) {
            throw new AccountNotFoundException();
        } else {
            repository.deleteById(accountId);
            repository.deleteCustomerAccountByAccountId(accountId);
            repository.updateCustomerAccountDeletedDate(accountId);
        }
    }
}
