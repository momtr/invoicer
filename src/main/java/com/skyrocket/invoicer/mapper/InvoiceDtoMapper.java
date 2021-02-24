package com.skyrocket.invoicer.mapper;

import com.skyrocket.invoicer.dto.InvoiceDto;
import com.skyrocket.invoicer.model.Invoice;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.stream.Collectors;

@Component
public class InvoiceDtoMapper {

    @Autowired
    private InvoicePositionDtoMapper invoicePositionDtoMapper;

    public InvoiceDto map(Invoice invoice) {
        InvoiceDto invoiceDto = InvoiceDto.builder()
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
                .positions(invoice.getPositions()
                        .stream()
                        .map(position -> invoicePositionDtoMapper.map(position))
                        .collect(Collectors.toList())
                ).build();
        if(invoice.getPaidAt() != null) {
            invoiceDto.setPaidAt(invoice.getPaidAt().getTime());
        }
        return invoiceDto;
    }

}
