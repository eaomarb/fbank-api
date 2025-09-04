package com.omar.fbank.account;

import com.omar.fbank.account.exception.AccountNotFoundException;
import com.omar.fbank.customer.Customer;
import com.omar.fbank.customer.CustomerRepository;
import com.omar.fbank.customer.exception.CustomerNotFoundException;
import com.omar.fbank.customeraccount.CustomerAccountService;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.iban4j.CountryCode;
import org.iban4j.Iban;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
@RequiredArgsConstructor
@Transactional
public class AccountService {
    private final AccountRepository repository;
    private final CustomerRepository customerRepository;
    private final CustomerAccountService customerAccountService;


    public Optional<Account> getAccountById(UUID accountId) {
        return Optional.ofNullable(repository.findById(accountId)
                .orElseThrow(AccountNotFoundException::new));
    }

    public List<Account> getAccounts() {
        return repository.findAll();
    }

    public Account createAccount(UUID customerId) {
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

        customerAccountService.create(customer, account);

        return account;
    }

    public void deleteAccount(UUID accountId) {
        if (!repository.existsById(accountId)) {
            throw new AccountNotFoundException();
        } else {
            repository.findById(accountId).orElseThrow().setStatus(AccountStatus.INACTIVE);
        }

    }

}
