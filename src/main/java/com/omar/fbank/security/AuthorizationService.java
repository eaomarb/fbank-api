package com.omar.fbank.security;

import com.omar.fbank.account.AccountRepository;
import com.omar.fbank.customer.CustomerRepository;
import com.omar.fbank.customeraccount.CustomerAccountRepository;
import com.omar.fbank.transaction.TransactionRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service("authService")
@RequiredArgsConstructor
@SuppressWarnings("unused")
public class AuthorizationService {

    private final CustomerRepository customerRepository;
    private final CustomerAccountRepository customerAccountRepository;
    private final AccountRepository accountRepository;
    private final TransactionRepository transactionRepository;
    private final SecurityUtils securityUtils;

    public boolean canAccessCustomer(UUID customerId) {
        UUID userId = securityUtils.getCurrentUserId();
        return customerRepository.existsByIdAndUserId(customerId, userId);
    }

    public boolean canCreateCustomer(UUID userId) {
        UUID currentUserId = securityUtils.getCurrentUserId();
        return currentUserId.equals(userId);
    }

    public boolean canAccessAccount(UUID accountId) {
        UUID userId = securityUtils.getCurrentUserId();
        return customerAccountRepository.existsByAccountIdAndCustomerUserId(accountId, userId);
    }

    public boolean canAccessAddress(UUID addressId) {
        UUID userId = securityUtils.getCurrentUserId();
        return customerRepository.existsByAddress_IdAndUser_Id(addressId, userId);
    }

    public boolean canAccessCustomerAccount(UUID customerAccountId) {
        UUID userId = securityUtils.getCurrentUserId();
        return customerAccountRepository.existsByIdAndCustomerUserId(customerAccountId, userId);
    }

    public boolean canModifyCustomerAccount(UUID customerAccountId) {
        UUID userId = securityUtils.getCurrentUserId();
        return customerAccountRepository.existsByIdAndOwnerIsTrueAndCustomer_User_Id(customerAccountId, userId);
    }


    public boolean canAccessTransaction(UUID transactionId) {
        UUID userId = securityUtils.getCurrentUserId();

        List<UUID> accountIds = customerAccountRepository.findAccountIdsByUserId(userId);

        return transactionRepository.findByIdAndAccountIds(transactionId, accountIds).isPresent();
    }

    public boolean canOperateOnAccount(UUID accountId) {
        UUID userId = securityUtils.getCurrentUserId();
        return customerAccountRepository.existsByAccountIdAndCustomerUserIdAndOwnerIsTrue(accountId, userId);
    }

    public boolean canAccessUser(UUID userId) {
        UUID currentUserId = securityUtils.getCurrentUserId();
        return currentUserId.equals(userId);
    }

    public boolean canModifyUser(UUID userId) {
        return canAccessUser(userId);
    }



}
