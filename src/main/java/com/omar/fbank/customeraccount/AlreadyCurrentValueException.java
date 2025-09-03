package com.omar.fbank.customeraccount;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.BAD_REQUEST)
public class AlreadyCurrentValueException extends RuntimeException {
    public AlreadyCurrentValueException(boolean isOwner) {
        super("Cannot update value. The current value is already " + isOwner);
    }
}