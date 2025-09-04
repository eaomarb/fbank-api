package com.omar.fbank.common;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.ConstraintViolationException;
import org.iban4j.IbanFormatException;
import org.iban4j.InvalidCheckDigitException;
import org.iban4j.UnsupportedCountryException;
import org.springframework.core.NestedExceptionUtils;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.*;

@ControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<Map<String, Object>> handleMethodArgumentNotValidatedException(
            MethodArgumentNotValidException ex,
            HttpServletRequest request) {

        List<String> errors = new ArrayList<>();

        for (ObjectError error : ex.getBindingResult().getAllErrors()) {
            errors.add(error.getDefaultMessage());
        }

        Map<String, Object> body = new HashMap<>();
        body.put("status", HttpStatus.BAD_REQUEST.value());
        body.put("path", request.getRequestURI());
        body.put("timestamp", DateTimeFormatter.ISO_DATE_TIME.format(LocalDateTime.now()));
        body.put("errors", errors);

        return ResponseEntity.badRequest().body(body);
    }

    @ExceptionHandler(DataIntegrityViolationException.class)
    public ResponseEntity<Map<String, Object>> handleDataIntegrityViolationException(
            DataIntegrityViolationException ex,
            HttpServletRequest request) {

        Map<String, Object> body = new HashMap<>();
        body.put("status", HttpStatus.BAD_REQUEST.value());
        body.put("path", request.getRequestURI());
        body.put("timestamp", DateTimeFormatter.ISO_DATE_TIME.format(LocalDateTime.now()));
        body.put("errors", NestedExceptionUtils.getMostSpecificCause(ex).getMessage());

        return ResponseEntity.badRequest().body(body);
    }

    @ExceptionHandler(ConstraintViolationException.class)
    public ResponseEntity<Map<String, Object>> handleConstraintViolationException(
            ConstraintViolationException ex,
            HttpServletRequest request
    ){
        Map<String, Object> body = new HashMap<>();
        body.put("status", HttpStatus.BAD_REQUEST.value());
        body.put("path", request.getRequestURI());
        body.put("timestamp", DateTimeFormatter.ISO_DATE_TIME.format(LocalDateTime.now()));
        ex.getConstraintViolations()
                .forEach(violation -> body.put(violation.getPropertyPath().toString(), violation.getMessageTemplate()));

        return ResponseEntity.badRequest().body(body);
    }

    @ExceptionHandler({
            IbanFormatException.class,
            InvalidCheckDigitException.class, UnsupportedCountryException.class
    })
    public ResponseEntity<Map<String, Object>> handleInvalidIbanException(
            Exception ex,
            HttpServletRequest request
    ){
        Map<String, Object> body = new HashMap<>();
        body.put("status", HttpStatus.BAD_REQUEST.value());
        body.put("path", request.getRequestURI());
        body.put("timestamp", DateTimeFormatter.ISO_DATE_TIME.format(LocalDateTime.now()));
        body.put("errors", ex.getMessage());

        return ResponseEntity.badRequest().body(body);
    }
}
