package com.skyrocket.invoicer.dto.requests;

import lombok.Getter;

@Getter
public class InvoicePositionRequest {
    private String uniqueCode;
    private String description;
    private int num;
    private double unitPrice;
    private String unit;
    private int vat;
}
