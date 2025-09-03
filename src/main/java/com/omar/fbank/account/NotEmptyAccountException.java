package com.omar.fbank.account;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.CONFLICT)
public class NotEmptyAccountException extends RuntimeException {
    public NotEmptyAccountException() {
        super("Account balance must be 0 to delete");
    }
}
