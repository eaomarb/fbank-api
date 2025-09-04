package com.omar.fbank.customeraccount.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value = HttpStatus.CONFLICT)
public class SelfAssignmentNotAllowedException extends RuntimeException {
    public SelfAssignmentNotAllowedException() {
        super("This customer is already linked to the account.");
    }
}
