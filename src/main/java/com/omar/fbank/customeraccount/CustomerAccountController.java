package com.omar.fbank.customeraccount;

import com.omar.fbank.customeraccount.dto.CustomerAccountResponseDto;
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
@RequestMapping("/api/customersAccounts")
@RequiredArgsConstructor
public class CustomerAccountController {
    private final CustomerAccountService service;

    @GetMapping("")
    @PreAuthorize("hasRole('ADMIN')")
    public Page<CustomerAccountResponseDto> getCustomerAccounts(Pageable pageable) {
        return service.getCustomerAccounts(pageable);
    }

    @GetMapping("/{customerAccountId}")
    @PreAuthorize("@authService.canAccessCustomerAccount(#customerAccountId)")
    public Optional<CustomerAccountResponseDto> getCustomerAccountById(@PathVariable UUID customerAccountId) {
        return service.getCustomerAccountDtoById(customerAccountId);
    }

    @GetMapping("/customer/{customerId}")
    @PreAuthorize("@authService.canCreateCustomerAccount(#customerId)")
    public Page<CustomerAccountResponseDto> getCustomerAccountsByCustomerId(@PathVariable UUID customerId, Pageable pageable) {
        return service.getCustomerAccountsByCustomerId(customerId, pageable);
    }

    @GetMapping("/account/{accountId}")
    @PreAuthorize("hasRole('ADMIN')")
    public Page<CustomerAccountResponseDto> getCustomerAccountByAccountId(@PathVariable UUID accountId, Pageable pageable) {
        return service.getCustomerAccountsDtoByAccountId(accountId, pageable);
    }

    @GetMapping("/{customerId}/{accountId}")
    @PreAuthorize("@authService.canCreateCustomerAccount(#customerId)")
    public Optional<CustomerAccountResponseDto> getCustomerAccountByCustomerAndAccountId(@PathVariable UUID customerId, @PathVariable UUID accountId) {
        return service.getCustomerAccountByCustomerAndAccountIds(customerId, accountId);
    }

    @PostMapping("/{accountId}/{customerId}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    @PreAuthorize("@authService.canCreateCustomerAccount(#customerId)")
    public void createCustomerAccount(@Valid @PathVariable UUID accountId,
                                      @Valid @PathVariable UUID customerId, Pageable pageable) {
        service.addCustomerToAccount(accountId, customerId, pageable);
    }

    @PatchMapping("/{customerAccountId}/ownership")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    @PreAuthorize("@authService.canModifyCustomerAccount(#customerAccountId)")
    public void updateCustomerAccountOwnership(@Valid @PathVariable UUID customerAccountId,
                                               @Valid @RequestParam boolean isOwner) {
        service.updateCustomerAccountOwnership(customerAccountId, isOwner);
    }

    @DeleteMapping("/{customerAccountId}")
    @PreAuthorize("@authService.canModifyCustomerAccount(#customerAccountId)")
    public void deleteCustomerAccountById(@PathVariable UUID customerAccountId) {
        service.deleteCustomerAccountById(customerAccountId);
    }

}
