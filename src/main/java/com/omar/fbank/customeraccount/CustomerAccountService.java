package com.omar.fbank.customeraccount;

import com.omar.fbank.account.*;
import com.omar.fbank.account.exception.AccountNotFoundException;
import com.omar.fbank.account.exception.NotEmptyAccountException;
import com.omar.fbank.customer.Customer;
import com.omar.fbank.customer.CustomerRepository;
import com.omar.fbank.customer.exception.CustomerNotFoundException;
import com.omar.fbank.customeraccount.exception.*;
import com.omar.fbank.transaction.exception.InactiveAccountException;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
@Transactional
@RequiredArgsConstructor
public class CustomerAccountService {
    private final CustomerAccountRepository repository;
    private final CustomerRepository customerRepository;
    private final AccountRepository accountRepository;

    public List<CustomerAccount> getCustomerAccounts() {
        return repository.findAll();
    }

    public Optional<CustomerAccount> getCustomerAccountById(UUID customerAccountId) {
        return Optional.ofNullable(repository.findById(customerAccountId).orElseThrow(CustomerAccountNotFoundException::new));
    }

    public List<CustomerAccount> getCustomerAccountsByCustomerId(UUID customerId) {
        Customer customer = customerRepository.findById(customerId).orElseThrow(CustomerNotFoundException::new);
        return repository.findByCustomer(customer);
    }

    public List<CustomerAccount> getCustomerAccountsByAccountId(UUID accountId) {
        Account account = accountRepository.findById(accountId).orElseThrow(AccountNotFoundException::new);
        return repository.findByAccount(account);
    }

    public Optional<CustomerAccount> getCustomerAccountByCustomerAndAccountIds(UUID customerId, UUID accountId) {
        Customer customer = customerRepository.findById(customerId).orElseThrow(CustomerNotFoundException::new);
        Account account = accountRepository.findById(accountId).orElseThrow(AccountNotFoundException::new);

        return Optional.ofNullable(repository.findByCustomerAndAccount(customer, account).orElseThrow(CustomerAccountNotFoundByCustomerAndAccountException::new));
    }

    public void create(Customer customer, Account account) {
        Optional<Customer> optionalCustomer = Optional.ofNullable(customer);
        Optional<Account> optionalAccount = Optional.ofNullable(account);

        if (optionalAccount.isPresent() && optionalAccount.orElseThrow().getStatus() == AccountStatus.INACTIVE) {
            throw new InactiveAccountException();
        }

        if (optionalCustomer.isPresent() && optionalAccount.isPresent()) {
            List<CustomerAccount> customerAccountList = getCustomerAccountsByAccountId(account.getId());

            for (CustomerAccount customerAccount : customerAccountList) {
                if (customerAccount.isOwner()) {
                    repository.save(new CustomerAccount(null, customer, account, false));
                    return;
                }
            }

            repository.save(new CustomerAccount(null, customer, account, true));
        }
    }

    public void addCustomerToAccount(UUID accountId, UUID customerId) {
        Customer customer = customerRepository.findById(customerId).orElseThrow(CustomerNotFoundException::new);
        Account account = accountRepository.findById(accountId).orElseThrow(AccountNotFoundException::new);

        Optional<CustomerAccount> customerAccount = repository.findByCustomerAndAccount(customer, account);

        if (customerAccount.isPresent()) {
            throw new SelfAssignmentNotAllowedException();
        }

        if (customer != null && account.getStatus() == AccountStatus.ACTIVE) {
            create(customer, account);
        }
    }


    public void updateCustomerAccountOwnership(UUID customerAccountId, boolean isOwner) {
        CustomerAccount customerAccount = getCustomerAccountById(customerAccountId)
                .orElseThrow(CustomerAccountNotFoundException::new);

        if (isOwner == customerAccount.isOwner()){
            throw new AlreadyCurrentValueException(isOwner);
        }

        if (customerAccount.isOwner()) {
            int ownerCount = repository.countByAccountAndIsOwner(customerAccount.getAccount().getId());

            if (ownerCount == 1) {
                throw new OnlyOneOwnerCustomerAccountException();
            }
        }

        customerAccount.setOwner(isOwner);
        repository.save(customerAccount);
    }

    public void deleteCustomerAccountById(UUID customerAccountId) {
        CustomerAccount customerAccount = getCustomerAccountById(customerAccountId)
                .orElseThrow(CustomerAccountNotFoundException::new);

        if (customerAccount.getAccount().getBalance().compareTo(BigDecimal.ZERO) != 0) {
            throw new NotEmptyAccountException();
        }

        int ownerCount = repository.countByAccountAndIsOwner(customerAccount.getAccount().getId());
        int customerCount = repository.countCustomerAccountByAccount(customerAccount.getAccount());

        if (customerAccount.isOwner() && ownerCount == 1 && customerCount > 1) {
            throw new OnlyOneOwnerCustomerAccountException();
        }

        repository.deleteById(customerAccountId);
    }
}
