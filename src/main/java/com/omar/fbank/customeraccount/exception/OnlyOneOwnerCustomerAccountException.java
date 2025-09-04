package com.omar.fbank.customeraccount.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.CONFLICT)
public class OnlyOneOwnerCustomerAccountException extends RuntimeException {
    public OnlyOneOwnerCustomerAccountException() {
        super("This account must have at least one owner.");
    }
}
