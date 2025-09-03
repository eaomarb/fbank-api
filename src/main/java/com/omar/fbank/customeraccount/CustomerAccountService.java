package com.omar.fbank.customeraccount;

import com.omar.fbank.account.*;
import com.omar.fbank.customer.Customer;
import com.omar.fbank.customer.CustomerRepository;
import com.omar.fbank.customer.exception.CustomerNotFoundException;
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

    public List<CustomerAccount> findAll() {
        return repository.findAll();
    }

    public Optional<CustomerAccount> findById(UUID id) {
        return Optional.ofNullable(repository.findById(id).orElseThrow(CustomerAccountNotFoundException::new));
    }

    public List<CustomerAccount> findByCustomerId(UUID customerId) {
        Customer customer = customerRepository.findById(customerId).orElseThrow(CustomerNotFoundException::new);
        return repository.findByCustomer(customer);
    }

    public List<CustomerAccount> findByAccountId(UUID accountId) {
        Account account = accountRepository.findById(accountId).orElseThrow(AccountNotFoundException::new);
        return repository.findByAccount(account);
    }

    public Optional<CustomerAccount> findByCustomerAndAccount(UUID customerId, UUID accountId) {
        Customer customer = customerRepository.findById(customerId).orElseThrow(CustomerNotFoundException::new);
        Account account = accountRepository.findById(accountId).orElseThrow(AccountNotFoundException::new);

        return Optional.ofNullable(repository.findByCustomerAndAccount(customer, account).orElseThrow(CustomerAccountNotFoundByCustomerAndAccountException::new));
    }

    public void create(Customer customer, Account account) {
        Optional<Customer> optionalCustomer = Optional.ofNullable(customer);
        Optional<Account> optionalAccount = Optional.ofNullable(account);

        if (optionalCustomer.isPresent() && optionalAccount.isPresent()) {
            List<CustomerAccount> customerAccountList = findByAccountId(account.getId());

            for (CustomerAccount customerAccount : customerAccountList) {
                if (customerAccount.isOwner()) {
                    repository.save(new CustomerAccount(null, customer, account, true));
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

        repository.save(new CustomerAccount(null, customer, account, false));
    }


    public void update(UUID id, boolean isOwner) {
        CustomerAccount customerAccount = findById(id)
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

    public void deleteById(UUID id) {
        CustomerAccount customerAccount = findById(id)
                .orElseThrow(CustomerAccountNotFoundException::new);

        if (customerAccount.getAccount().getBalance().compareTo(BigDecimal.ZERO) != 0) {
            throw new NotEmptyAccountException();
        }

        int ownerCount = repository.countByAccountAndIsOwner(customerAccount.getAccount().getId());
        int customerCount = repository.countCustomerAccountByAccount(customerAccount.getAccount());

        if (customerAccount.isOwner() && ownerCount == 1 && customerCount > 1) {
            throw new OnlyOneOwnerCustomerAccountException();
        }

        repository.deleteById(id);
    }
}
