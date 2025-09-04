package com.omar.fbank.customeraccount.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.NOT_FOUND)
public class CustomerAccountNotFoundByCustomerAndAccountException extends RuntimeException {
    public CustomerAccountNotFoundByCustomerAndAccountException() {
        super("CustomerAccount Not Found with the given customerId and accountId");
    }
}
