package com.omar.fbank.transaction;

import com.omar.fbank.account.Account;
import com.omar.fbank.account.AccountRepository;
import com.omar.fbank.account.AccountStatus;
import com.omar.fbank.customeraccount.CustomerAccount;
import com.omar.fbank.customeraccount.CustomerAccountRepository;
import com.omar.fbank.transaction.dto.*;
import com.omar.fbank.transaction.exception.IbanNullException;
import com.omar.fbank.transaction.exception.InactiveAccountException;
import com.omar.fbank.transaction.exception.TransactionNotFoundException;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.iban4j.IbanUtil;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
@Transactional
@RequiredArgsConstructor
public class TransactionService {
    private final TransactionRepository repository;
    private final TransactionDtoMapper mapper;
    private final AccountRepository accountRepository;
    private final CustomerAccountRepository customerAccountRepository;

    public List<TransactionResponseDto> getTransactions() {
        return repository
                .findAll(Sort.by(Sort.Direction.DESC, "transactionDate"))
                .stream()
                .map(mapper::toResponseDto)
                .toList();
    }

    public TransactionResponseDto getTransactionById(UUID transactionId) {
        Transaction transaction = repository.findById(transactionId).orElseThrow(TransactionNotFoundException::new);

        return mapper.toResponseDto(transaction);
    }

    public List<TransactionResponseDto> getTransactionsByAccountId(UUID accountId) {
        return repository.findByAccountIdOrderByTransactionDate(accountId);
    }

    public List<TransactionResponseDto> getTransactionsByCustomerId(UUID customerId) {
        List<CustomerAccount> customerAccounts = customerAccountRepository.findByCustomer_Id(customerId);

        List<UUID> accounts = customerAccounts
                .stream()
                .map(customerAccount -> customerAccount.getAccount().getId())
                .toList();

        System.out.println(repository.findByAccountIdOrderByTransactionDateAsc(accounts));

        return repository
                .findByAccountIdOrderByTransactionDateAsc(accounts)
                .stream()
                .map(mapper::toResponseDto)
                .toList();
    }

    public TransactionResponseDto deposit(DepositRequestDto depositRequestDto) {
        Transaction deposit = mapper.toEntity(depositRequestDto);

        if (deposit.account.getStatus() == AccountStatus.INACTIVE){
            throw new InactiveAccountException();
        }

        deposit.setBeneficiaryIban(deposit.getAccount().getIban());
        deposit.setTransactionType(TransactionType.DEPOSIT);
        deposit.setTransactionStatus(TransactionStatus.COMPLETED);

        Account account = deposit.getAccount();
        account.setBalance(account.getBalance().add(deposit.getAmount()));

        repository.saveAndFlush(deposit);
        return mapper.toResponseDto(deposit);
    }

    public TransactionResponseDto withdraw(WithdrawRequestDto withdrawRequestDto) {
        Transaction withdraw = mapper.toEntity(withdrawRequestDto);
        Account account = withdraw.account;

        if (account.getStatus() == AccountStatus.INACTIVE){
            throw new InactiveAccountException();
        }

        if (account.getBalance().compareTo(withdrawRequestDto.amount()) < 0) {
            withdraw.setTransactionStatus(TransactionStatus.FAILED);
        } else {
            withdraw.setTransactionStatus(TransactionStatus.COMPLETED);
            account.setBalance(account.getBalance().subtract(withdraw.getAmount()));
        }

        repository.saveAndFlush(withdraw);
        return mapper.toResponseDto(withdraw);
    }

    public TransactionResponseDto transfer(TransferRequestDto transferRequestDto) {
        Transaction transfer = mapper.toEntity(transferRequestDto);
        Account originAccount = transfer.account;

        if (originAccount.getStatus() == AccountStatus.INACTIVE){
            throw new InactiveAccountException();
        }

        Account beneficiaryAccount = accountRepository.findAccountByIban(transferRequestDto.beneficiaryIban());

        if (transferRequestDto.beneficiaryIban() != null) {
            IbanUtil.validate(transferRequestDto.beneficiaryIban());
        } else {
            throw new IbanNullException();
        }

        if (originAccount.getBalance().compareTo(transferRequestDto.amount()) < 0) {
            transfer.setTransactionStatus(TransactionStatus.FAILED);
        } else {
            transfer.setTransactionStatus(TransactionStatus.COMPLETED);
            originAccount.setBalance(originAccount.getBalance().subtract(transferRequestDto.amount()));
        }

        if (beneficiaryAccount != null) {
            beneficiaryAccount.setBalance(beneficiaryAccount.getBalance().add(transferRequestDto.amount()));
        }

        repository.saveAndFlush(transfer);
        return mapper.toResponseDto(transfer);
    }
}
