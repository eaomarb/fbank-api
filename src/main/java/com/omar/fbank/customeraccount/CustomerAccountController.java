package com.omar.fbank.customeraccount;

import com.omar.fbank.customeraccount.dto.CustomerAccountResponseDto;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;
import java.util.UUID;

@RestController
@RequestMapping("/api/customersAccounts")
@RequiredArgsConstructor
@Tag(name = "CustomerAccounts", description = "Manage which customers are associated with which accounts and their ownership")
public class CustomerAccountController {
    private final CustomerAccountService service;

    @GetMapping("")
    @PreAuthorize("hasAuthority('ADMIN')")
    @Operation(summary = "List all customer accounts", description = "Returns all customer-account relations. Only accessible by admin.")
    public Page<CustomerAccountResponseDto> getCustomerAccounts(Pageable pageable) {
        return service.getCustomerAccounts(pageable);
    }

    @GetMapping("/{customerAccountId}")
    @PreAuthorize("hasAuthority('ADMIN') or @authService.canAccessCustomerAccount(#customerAccountId)")
    @Operation(summary = "Get customer-account details", description = "Returns the details of a specific customer-account relation if permitted or admin.")
    public Optional<CustomerAccountResponseDto> getCustomerAccountById(@Parameter(description = "ID of the customer-account") @PathVariable UUID customerAccountId) {
        return service.getCustomerAccountDtoById(customerAccountId);
    }

    @GetMapping("/customer/{customerId}")
    @PreAuthorize("hasAuthority('ADMIN') or @authService.canAccessCustomer(#customerId)")
    @Operation(summary = "List customer accounts for a customer", description = "Returns all accounts associated with a given customer. Accessible by admin or the customer itself.")
    public Page<CustomerAccountResponseDto> getCustomerAccountsByCustomerId(@Parameter(description = "ID of the customer") @PathVariable UUID customerId, Pageable pageable) {
        return service.getCustomerAccountsByCustomerId(customerId, pageable);
    }

    @GetMapping("/account/{accountId}")
    @PreAuthorize("hasAuthority('ADMIN')")
    @Operation(summary = "List customers for an account", description = "Returns all customers associated with a specific account. Only admin can access.")
    public Page<CustomerAccountResponseDto> getCustomerAccountByAccountId(@Parameter(description = "ID of the account") @PathVariable UUID accountId, Pageable pageable) {
        return service.getCustomerAccountsDtoByAccountId(accountId, pageable);
    }

    @GetMapping("/{customerId}/{accountId}")
    @PreAuthorize("hasAuthority('ADMIN') or @authService.canAccessAccount(#accountId)")
    @Operation(summary = "Get specific customer account relation", description = "Returns the relationship between a specific customer and account. Accessible by admin or authorized user.")
    public Optional<CustomerAccountResponseDto> getCustomerAccountByCustomerAndAccountId(@Parameter(description = "ID of the customer") @PathVariable UUID customerId,
                                                                                         @Parameter(description = "ID of the account") @PathVariable UUID accountId) {
        return service.getCustomerAccountByCustomerAndAccountIds(customerId, accountId);
    }


    @PostMapping("/{accountId}/{customerId}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    @PreAuthorize("hasAuthority('ADMIN') or @authService.canOperateOnAccount(#accountId)")
    @Operation(summary = "Add a customer to an account", description = "Associates a customer with an account. Accessible by admin or users permitted to operate on the account.")
    public void addCustomerToAccount(@Parameter(description = "ID of the account") @PathVariable UUID accountId,
                                     @Parameter(description = "ID of the customer") @PathVariable UUID customerId, Pageable pageable) {
        service.addCustomerToAccount(accountId, customerId, pageable);
    }

    @PatchMapping("/{customerAccountId}/ownership")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    @PreAuthorize("hasAuthority('ADMIN') or @authService.canModifyCustomerAccount(#customerAccountId)")
    @Operation(summary = "Change ownership of a customer in an account", description = "Change whether a customer is the owner of an account.")
    public void updateCustomerAccountOwnership(@Parameter(description = "ID of the customer-account") @PathVariable UUID customerAccountId,
                                               @Parameter(description = "Ownership status to set") @RequestParam boolean isOwner) {
        service.updateCustomerAccountOwnership(customerAccountId, isOwner);
    }

    @DeleteMapping("/{customerAccountId}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    @PreAuthorize("hasAuthority('ADMIN') or @authService.canModifyCustomerAccount(#customerAccountId)")
    @Operation(summary = "Remove a customer from an account", description = "Remove a customer from an account if allowed.")
    public void deleteCustomerAccountById(@Parameter(description = "ID of the customer-account") @PathVariable UUID customerAccountId) {
        service.deleteCustomerAccountById(customerAccountId);
    }

}
