package com.omar.fbank.customeraccount;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value = HttpStatus.NOT_FOUND)
public class CustomerAccountNotFoundException extends RuntimeException {
    public CustomerAccountNotFoundException() {
        super("Customer Account Not Found");
    }
}
