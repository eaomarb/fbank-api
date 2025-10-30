package com.omar.fbank.customer;

import com.omar.fbank.account.AccountService;
import com.omar.fbank.account.dto.AccountResponseDto;
import com.omar.fbank.customer.dto.CustomerRequestDto;
import com.omar.fbank.customer.dto.CustomerResponseDto;
import com.omar.fbank.transaction.TransactionService;
import com.omar.fbank.transaction.dto.TransactionResponseDto;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
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
@Tag(name = "Customers", description = "Manage customers and their accounts")
public class CustomerController {
    private final CustomerService service;
    private final AccountService accountService;
    private final TransactionService transactionService;

    @GetMapping("/{customerId}")
    @PreAuthorize("hasAuthority('ADMIN') or @authService.canAccessCustomer(#customerId)")
    @Operation(summary = "Get customer by ID", description = "Returns customer details if the user has access or is an admin")
    public Optional<CustomerResponseDto> getCustomerById(@Parameter(description = "UUID of the customer") @PathVariable UUID customerId) {
        return service.getCustomerDtoById(customerId);
    }

    @GetMapping("/user/{userId}")
    @PreAuthorize("hasAuthority('ADMIN') or @authService.canAccessUser(#userId)")
    @Operation(summary = "Get customer by User ID", description = "Returns customer details if the user has access or is an admin")
    public Optional<CustomerResponseDto> getCustomerByUserId(@Parameter(description = "UUID of the user") @PathVariable UUID userId) {
        return service.getCustomerDtoByUserId(userId);
    }

    @GetMapping("")
    @PreAuthorize("hasAuthority('ADMIN')")
    @Operation(summary = "Get all customers (admin only)", description = "Returns a list of customers")
    public Page<CustomerResponseDto> getCustomers(Pageable pageable) {
        return service.getCustomersDto(pageable);
    }

    @GetMapping("{customerId}/transactions")
    @PreAuthorize("hasAuthority('ADMIN') or @authService.canAccessCustomer(#customerId)")
    @Operation(summary = "Get transactions for customer", description = "Returns transactions for a specific customer")
    public Page<TransactionResponseDto> getTransactionsByCustomerId(@Parameter(description = "UUID of the customer") @PathVariable UUID customerId,
                                                                    Pageable pageable) {
        return transactionService.getTransactionsDtoByCustomerId(customerId, pageable);
    }

    @PostMapping("/{userId}")
    @ResponseStatus(HttpStatus.CREATED)
    @PreAuthorize("hasAuthority('ADMIN') or @authService.canCreateCustomer(#userId)")
    @Operation(summary = "Create customer", description = "Creates a new customer for the given user if permitted or if the user is an admin")
    public CustomerResponseDto createCustomer(@Parameter(description = "UUID of the user") @PathVariable UUID userId,
                                              @Valid @RequestBody CustomerRequestDto customerRequestDto) {
        return service.createCustomer(customerRequestDto, userId);
    }

    @PostMapping("/{customerId}/accounts")
    @ResponseStatus(HttpStatus.CREATED)
    @PreAuthorize("hasAuthority('ADMIN') or @authService.canAccessCustomer(#customerId)")
    @Operation(summary = "Create account for customer", description = "Creates a new account for a specific customer if permitted or if the user is an admin")
    public AccountResponseDto createAccount(@Parameter(description = "UUID of the customer") @Valid @PathVariable UUID customerId, Pageable pageable) {
        return accountService.createAccount(customerId, pageable);
    }

    @PutMapping("/{customerId}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    @PreAuthorize("hasAuthority('ADMIN') or @authService.canAccessCustomer(#customerId)")
    @Operation(summary = "Update customer", description = "Updates customer details if permitted or if the user is an admin")
    public void updateCustomer(@Parameter(description = "UUID of the customer") @PathVariable UUID customerId,
                               @Valid @RequestBody CustomerRequestDto customerRequestDto) {
        service.updateCustomer(customerId, customerRequestDto);
    }

    @DeleteMapping("/{customerId}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    @PreAuthorize("hasAuthority('ADMIN') or @authService.canAccessCustomer(#customerId)")
    @Operation(summary = "Delete customer", description = "Deletes a customer if permitted or if the user is an admin")
    public void deleteCustomer(@Parameter(description = "UUID of the customer") @PathVariable UUID customerId) {
        service.deleteCustomer(customerId);
    }

    @PostMapping("/{customerId}/reactivate")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    @PreAuthorize("hasAuthority('ADMIN') or @authService.canAccessCustomer(#customerId)")
    @Operation(summary = "Reactivate customer", description = "Reactivates a customer if permitted or if the user is an admin")
    public void reactivateCustomer(@Parameter(description = "UUID of the customer") @PathVariable UUID customerId) {
        service.reactivateCustomer(customerId);
    }


}