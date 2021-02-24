package com.skyrocket.invoicer.mapper;

import com.skyrocket.invoicer.dto.InvoiceDto;
import com.skyrocket.invoicer.dto.InvoiceProfileDto;
import com.skyrocket.invoicer.model.Invoice;
import org.springframework.stereotype.Component;

@Component
public class InvoiceProfileDtoMapper {

    public InvoiceProfileDto map(Invoice invoice) {
        InvoiceProfileDto invoiceProfileDto = InvoiceProfileDto.builder()
                .id(invoice.getId().longValue())
                .invoiceName(invoice.getInvoiceName())
                .issuedAt(invoice.getIssuedAt().getTime())
                .issuerAddress(invoice.getIssuerAddress())
                .issuerEmail(invoice.getIssuerEmail())
                .issuerLogo(invoice.getIssuerLogo())
                .payUntil(invoice.getPayUntil().getTime())
                .recAddress(invoice.getRecAddress())
                .recEmail(invoice.getRecEmail())
                .recName(invoice.getRecName())
                .issuerName(invoice.getIssuerName())
                .skontoNote(invoice.getSkontoNote())
                .vatId(invoice.getVatId())
                .totalPriceWithoutVat(invoice.getTotalPriceWithoutVat())
                .totalPriceWithVat(invoice.getTotalPriceWithVat())
                .paid(invoice.isPaid())
                .issuerIban(invoice.getIssuerIban())
                .build();
        if(invoice.getPaidAt() != null) {
            invoiceProfileDto.setPaidAt(invoice.getPaidAt().getTime());
        }
        return invoiceProfileDto;
    }

}
