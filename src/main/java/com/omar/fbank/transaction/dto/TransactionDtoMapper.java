package com.omar.fbank.transaction.dto;

import com.omar.fbank.account.Account;
import com.omar.fbank.account.AccountService;
import com.omar.fbank.transaction.Transaction;
import com.omar.fbank.transaction.TransactionType;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.time.ZoneId;

@Component
public class TransactionDtoMapper {
    private final AccountService accountService;

    public TransactionDtoMapper(AccountService accountService) {
        this.accountService = accountService;
    }

    public Transaction toEntity(TransferRequestDto transferRequestDto) {
        Transaction transaction = new Transaction();
        Account account = accountService.getAccountById(transferRequestDto.accountId()).orElseThrow();

        transaction.setAccount(account);
        transaction.setAmount(transferRequestDto.amount());
        transaction.setConcept(transferRequestDto.concept());
        transaction.setBeneficiaryName(transferRequestDto.beneficiaryName());
        transaction.setBeneficiaryIban(transferRequestDto.beneficiaryIban());
        transaction.setTransactionType(TransactionType.TRANSFER);

        return transaction;
    }

    public Transaction toEntity(DepositRequestDto depositRequestDto) {
        Transaction transaction = new Transaction();
        Account account = accountService.getAccountById(depositRequestDto.accountId()).orElseThrow();

        transaction.setAccount(account);
        transaction.setAmount(depositRequestDto.amount());
        transaction.setBeneficiaryIban(account.getIban());
        transaction.setConcept(depositRequestDto.concept());
        transaction.setTransactionType(TransactionType.DEPOSIT);

        return transaction;
    }

    public Transaction toEntity(WithdrawRequestDto withdrawRequestDto) {
        Transaction transaction = new Transaction();
        Account account = accountService.getAccountById(withdrawRequestDto.accountId()).orElseThrow();

        transaction.setAccount(account);
        transaction.setAmount(withdrawRequestDto.amount());
        transaction.setBeneficiaryIban(account.getIban());
        transaction.setConcept(withdrawRequestDto.concept());
        transaction.setTransactionType(TransactionType.WITHDRAW);

        return transaction;
    }

    public TransactionResponseDto toResponseDto(Transaction transaction) {
        return new TransactionResponseDto(
                transaction.getId(),
                LocalDateTime.ofInstant(transaction.getCreatedAt(), ZoneId.systemDefault()),
                transaction.getAccount().getId(),
                transaction.getAmount(),
                transaction.getBeneficiaryName(),
                transaction.getConcept(),
                transaction.getBeneficiaryIban(),
                transaction.getTransactionStatus(),
                transaction.getTransactionType()
        );
    }
}
