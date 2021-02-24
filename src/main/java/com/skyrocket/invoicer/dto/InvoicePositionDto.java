package com.skyrocket.invoicer.dto;

import lombok.*;

import java.sql.Timestamp;

@Data
@Builder
@AllArgsConstructor
@EqualsAndHashCode
@ToString
public class InvoicePositionDto {
    private long id;
    private String uniqueCode;
    private String description;
    private int num;
    private double unitPrice;
    private String unit;
    private int vat;
    private long addedAt;
    private double unitPriceWithVat;
    private double totalPriceWithoutVat;
    private double totalPriceWithVat;
}
