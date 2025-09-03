package com.omar.fbank.account;

import com.omar.fbank.customer.Customer;
import com.omar.fbank.customer.CustomerRepository;
import com.omar.fbank.customeraccount.CustomerAccount;
import com.omar.fbank.customeraccount.CustomerAccountRepository;
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
    private final CustomerAccountRepository customerAccountRepository;
    private final CustomerAccountService customerAccountService;


    public Optional<Account> findAccountById(UUID accountId) {
        return Optional.ofNullable(repository.findById(accountId)
                .orElseThrow(com.omar.fbank.account.AccountNotFoundException::new));
    }

    public List<Account> findAccounts() {
        return repository.findAll();
    }

    public Account createAccount(UUID customerId) {
        Account account = new Account();
        Customer customer = customerRepository.getCustomerById(customerId);

        String iban;
        do {
            iban = Iban.random(CountryCode.ES).toString();
        } while (repository.existsByIban(iban));

        account.setIban(iban);
        account.setBalance(BigDecimal.valueOf(0));
        repository.save(account);

        customerAccountService.create(customer, account);

        return account;
    }

    public void addCustomerToAccount(UUID accountId, UUID customerId) {
        Customer customer = customerRepository.getCustomerById(customerId);
        Account account = findAccountById(accountId).orElseThrow();

        if (customer != null) {
            customerAccountService.create(customer, account);
        }

    }

    public void deleteAccount(UUID accountId) {
        if (!repository.existsById(accountId)) {
            throw new com.omar.fbank.account.AccountNotFoundException();
        }
        List<CustomerAccount> customersAccounts = customerAccountService.findByAccountId(accountId);
        customerAccountRepository.deleteAll(customersAccounts);
        repository.deleteById(accountId);
    }

}
