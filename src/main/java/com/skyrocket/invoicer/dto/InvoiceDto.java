package com.skyrocket.invoicer.dto;

import com.skyrocket.invoicer.model.InvoicePosition;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;

import javax.persistence.OneToMany;
import java.sql.Timestamp;
import java.util.List;

@Data
@Builder
@AllArgsConstructor
@EqualsAndHashCode
public class InvoiceDto {
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
    private List<InvoicePositionDto> positions;
    private String currency;
    private double totalPriceWithoutVat;
    private double totalPriceWithVat;
    private boolean paid;
    private long paidAt;
    private String issuerIban;
}
