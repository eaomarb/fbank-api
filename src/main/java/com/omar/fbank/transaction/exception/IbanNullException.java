package com.omar.fbank.transaction.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.BAD_REQUEST)
public class IbanNullException extends RuntimeException {
    public IbanNullException() {
        super("Beneficiary IBAN is null");
    }
}
