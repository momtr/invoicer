package com.skyrocket.invoicer.dto;

import lombok.*;

@Data
@Builder
@AllArgsConstructor
@EqualsAndHashCode
public class InvoiceProfileDto {
    private long id;
    private String invoiceName;
    private long issuedAt;
    private String issuerLogo;
    private String issuerName;
    private String issuerEmail;
    private String issuerAddress;
    private String vatId;
    private String recName;
    private String recAddress;
    private String recEmail;
    private long payUntil;
    private String skontoNote;
    private String currency;
    private double totalPriceWithoutVat;
    private double totalPriceWithVat;
    private boolean paid;
    private long paidAt;
    private String issuerIban;
}
