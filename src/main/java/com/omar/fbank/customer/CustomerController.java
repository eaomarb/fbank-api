package com.omar.fbank.customer;

import com.omar.fbank.account.AccountService;
import com.omar.fbank.account.dto.AccountResponseDto;
import com.omar.fbank.customer.dto.CustomerRequestDto;
import com.omar.fbank.customer.dto.CustomerResponseDto;
import com.omar.fbank.transaction.TransactionService;
import com.omar.fbank.transaction.dto.TransactionResponseDto;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;
import java.util.UUID;

@RestController
@RequestMapping("/api/customers")
@RequiredArgsConstructor
public class CustomerController {
    private final CustomerService service;
    private final AccountService accountService;
    private final TransactionService transactionService;

    @GetMapping("/{customerId}")
    @PreAuthorize("hasAuthority('ADMIN') or @authService.canAccessCustomer(#customerId)")
    public Optional<CustomerResponseDto> getCustomerById(@PathVariable UUID customerId) {
        return service.getCustomerDtoById(customerId);
    }

    @GetMapping("")
    @PreAuthorize("hasAuthority('ADMIN')")
    public Page<CustomerResponseDto> getCustomers(Pageable pageable) {
        return service.getCustomersDto(pageable);
    }

    @GetMapping("{customerId}/transactions")
    @PreAuthorize("hasAuthority('ADMIN') or @authService.canAccessCustomer(#customerId)")
    public Page<TransactionResponseDto> getTransactionsByCustomerId(@PathVariable UUID customerId,
                                                                    Pageable pageable) {
        return transactionService.getTransactionsDtoByCustomerId(customerId, pageable);
    }

    @PostMapping("/{userId}")
    @ResponseStatus(HttpStatus.CREATED)
    @PreAuthorize("hasAuthority('ADMIN') or @authService.canCreateCustomer(#userId)")
    public CustomerResponseDto createCustomer(@PathVariable UUID userId,
                                              @Valid @RequestBody CustomerRequestDto customerRequestDto) {
        return service.createCustomer(customerRequestDto, userId);
    }

    @PostMapping("/{customerId}/accounts")
    @ResponseStatus(HttpStatus.CREATED)
    @PreAuthorize("hasAuthority('ADMIN') or @authService.canAccessCustomer(#customerId)")
    public AccountResponseDto createAccount(@Valid @PathVariable UUID customerId, Pageable pageable) {
        return accountService.createAccount(customerId, pageable);
    }

    @PutMapping("/{customerId}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    @PreAuthorize("hasAuthority('ADMIN') or @authService.canAccessCustomer(#customerId)")
    public void updateCustomer(@PathVariable UUID customerId,
                               @Valid @RequestBody CustomerRequestDto customerRequestDto) {
        service.updateCustomer(customerId, customerRequestDto);
    }

    @DeleteMapping("/{customerId}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    @PreAuthorize("hasAuthority('ADMIN') or @authService.canAccessCustomer(#customerId)")
    public void deleteCustomer(@PathVariable UUID customerId) {
        service.deleteCustomer(customerId);
    }

    @PostMapping("/{customerId}/reactivate")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    @PreAuthorize("hasAuthority('ADMIN') or @authService.canAccessCustomer(#customerId)")
    public void reactivateCustomer(@PathVariable UUID customerId) {
        service.reactivateCustomer(customerId);
    }


}