package com.omar.fbank.transaction.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value = HttpStatus.BAD_REQUEST)
public class InactiveAccountException extends RuntimeException {
    public InactiveAccountException() {
        super("Account is inactive.");
    }
}
