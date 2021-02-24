package com.skyrocket.invoicer.exception;

public class InvoiceDoesNotExistException extends Exception {

    public InvoiceDoesNotExistException(long invoiceId) {
        super(String.format("invoice with running number [%s] does not exist", invoiceId));
    }

}
