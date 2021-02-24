package com.skyrocket.invoicer.mapper;

import com.skyrocket.invoicer.dto.InvoicePositionDto;
import com.skyrocket.invoicer.model.InvoicePosition;
import org.springframework.stereotype.Component;

@Component
public class InvoicePositionDtoMapper {

    public InvoicePositionDto map(InvoicePosition invoicePosition) {
        return InvoicePositionDto.builder()
                .addedAt(invoicePosition.getAddedAt().getTime())
                .addedAt(invoicePosition.getAddedAt().getTime())
                .description(invoicePosition.getDescription())
                .id(invoicePosition.getId().longValue())
                .num(invoicePosition.getNum())
                .addedAt(invoicePosition.getAddedAt().getTime())
                .unitPrice(invoicePosition.getUnitPrice())
                .unit(invoicePosition.getUnit())
                .uniqueCode(invoicePosition.getUniqueCode())
                .addedAt(invoicePosition.getAddedAt().getTime())
                .vat(invoicePosition.getVat())
                .unitPriceWithVat(invoicePosition.getUnitPriceWithVat())
                .totalPriceWithoutVat(invoicePosition.getTotalPriceWithoutVat())
                .totalPriceWithVat(invoicePosition.getTotalPriceWithVat())
                .build();
    }

}
