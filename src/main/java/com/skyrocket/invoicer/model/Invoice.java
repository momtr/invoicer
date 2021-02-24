package com.skyrocket.invoicer.model;

import lombok.*;

import javax.persistence.*;
import java.sql.Timestamp;
import java.util.List;

@Entity
@Data
@Builder
@AllArgsConstructor
@EqualsAndHashCode
@ToString
public class Invoice {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    private String invoiceName;
    private Timestamp issuedAt;

    private String issuerLogo;
    private String issuerName;
    private String issuerEmail;
    private String issuerAddress;
    private String vatId;
    private String issuerIban;

    private String recName;
    private String recAddress;
    private String recEmail;

    private Timestamp payUntil;
    private String skontoNote;

    private String currency;

    private double totalPriceWithoutVat;
    private double totalPriceWithVat;

    private boolean paid;
    private Timestamp paidAt;

    private String pdfLocation;

    @OneToMany
    private List<InvoicePosition> positions;

    public Invoice() { }

}