package com.skyrocket.invoicer.dto.requests;

import lombok.Getter;

@Getter
public class InvoiceRequest {
    private String invoiceName;
    private String recName;
    private String recAddress;
    private String recEmail;
    private long payUntil;
    private String skontoNote;
}
