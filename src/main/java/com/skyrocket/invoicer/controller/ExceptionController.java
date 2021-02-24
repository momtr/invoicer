package com.skyrocket.invoicer.controller;

import com.skyrocket.invoicer.exception.InvoiceDoesNotExistException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.Map;

@RestController
@ControllerAdvice
@Slf4j
public class ExceptionController {

    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public Map<String, Object> handleValidationExceptions(MethodArgumentNotValidException ex) {
        log.info("handle validation exception");
        Map<String, String> errors = new HashMap<>();
        ex.getBindingResult().getAllErrors().forEach(error -> {
            String fieldName = ((FieldError) error).getField();
            String errorMessage = error.getDefaultMessage();
            errors.put(fieldName, errorMessage);
        });
        Map<String, Object> map = new HashMap<>();
        map.put("fields", errors);
        map.put("type", "VALIDATION_ERROR");
        return map;
    }

    @ExceptionHandler(InvoiceDoesNotExistException.class)
    public Map<String, String> handleInvoiceDoesNotExist(InvoiceDoesNotExistException ex) {
        log.info("EXC invoiceDoesNotExist [{}]", ex.getMessage());
        Map<String, String> map = new HashMap<>();
        map.put("error", ex.getMessage());
        map.put("type", "INVOICE_DOES_NOT_EXIST");
        return map;
    }

}
