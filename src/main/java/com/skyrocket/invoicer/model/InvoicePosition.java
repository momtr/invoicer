package com.skyrocket.invoicer.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import java.sql.Timestamp;

@Entity
@Data
@Builder
@AllArgsConstructor
@EqualsAndHashCode
public class InvoicePosition {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    private String uniqueCode;
    private String description;
    private int num;
    private int vat;
    private Timestamp addedAt;

    private String unit;
    private double unitPrice;
    private double unitPriceWithVat;
    private double totalPriceWithoutVat;
    private double totalPriceWithVat;

    public InvoicePosition() { }

}
