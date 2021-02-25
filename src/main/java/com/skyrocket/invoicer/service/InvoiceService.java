package com.skyrocket.invoicer.service;

import com.lowagie.text.DocumentException;
import com.skyrocket.invoicer.dto.InvoiceDto;
import com.skyrocket.invoicer.dto.InvoiceProfileDto;
import com.skyrocket.invoicer.dto.requests.InvoicePositionRequest;
import com.skyrocket.invoicer.dto.requests.InvoiceRequest;
import com.skyrocket.invoicer.exception.InvoiceDoesNotExistException;
import com.skyrocket.invoicer.mapper.InvoiceDtoMapper;
import com.skyrocket.invoicer.mapper.InvoiceProfileDtoMapper;
import com.skyrocket.invoicer.model.Invoice;
import com.skyrocket.invoicer.model.InvoicePosition;
import com.skyrocket.invoicer.repository.InvoicePositionRepository;
import com.skyrocket.invoicer.repository.InvoiceRepository;
import com.skyrocket.invoicer.util.InvoicePdfUtil;
import com.skyrocket.invoicer.util.mail.MailSender;
import com.skyrocket.invoicer.util.mail.RecipientList;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import javax.mail.MessagingException;
import java.io.*;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Optional;

@Service
@Slf4j
public class InvoiceService {

    @Autowired
    private InvoiceRepository invoiceRepository;

    @Autowired
    private InvoicePositionRepository invoicePositionRepository;

    @Autowired
    private InvoiceDtoMapper invoiceDtoMapper;

    @Autowired
    private InvoiceProfileDtoMapper invoiceProfileDtoMapper;

    @Autowired
    private InvoicePdfUtil invoicePdfUtil;

    @Autowired
    private MailSender mailSender;

    private final String ISSUER_LOGO = "https://github.com/momtr/skyrocket-agency/blob/master/static/graphics/logo_primary.png?raw=true";
    private final String ISSUER_NAME = "Skyrocket Agency GmbH";
    private final String ISSUER_EMAIL = "office@skyrocket.at";
    private final String ISSUER_ADDRESS = "Stephansplatz 1, 1010 Wien";
    private final String ISSUER_VAT_ID = "VAT-ID 1923923929";
    private final String ISSUER_IBAN = "IBAN CH93 0076 2011 6238 5295 7";
    private final String CURRENCY = "EUR";

    public InvoiceDto createInvoice(InvoiceRequest invoiceRequest) throws InvoiceDoesNotExistException {
        Invoice invoice = Invoice.builder()
                .issuedAt(new Timestamp(System.currentTimeMillis()))
                .issuerAddress(ISSUER_ADDRESS)
                .issuerEmail(ISSUER_EMAIL)
                .issuerLogo(ISSUER_LOGO)
                .issuerName(ISSUER_NAME)
                .invoiceName(invoiceRequest.getInvoiceName())
                .payUntil(new Timestamp(invoiceRequest.getPayUntil()))
                .recAddress(invoiceRequest.getRecAddress())
                .recEmail(invoiceRequest.getRecEmail())
                .recName(invoiceRequest.getRecName())
                .skontoNote(invoiceRequest.getSkontoNote())
                .vatId(ISSUER_VAT_ID)
                .positions(new ArrayList<>())
                .currency(CURRENCY)
                .paid(false)
                .issuerIban(ISSUER_IBAN)
                .build();
        saveInvoice(invoice);
        return getInvoice(invoice.getId().longValue());
    }

    public void addInvoicePosition(long invoideId, InvoicePositionRequest invoicePositionRequest) throws InvoiceDoesNotExistException {
        Invoice invoice = findInvoice(invoideId);
        InvoicePosition invoicePosition = InvoicePosition.builder()
                .addedAt(new Timestamp(System.currentTimeMillis()))
                .description(invoicePositionRequest.getDescription())
                .num(invoicePositionRequest.getNum())
                .unitPrice(invoicePositionRequest.getUnitPrice())
                .unit(invoicePositionRequest.getUnit())
                .uniqueCode(invoicePositionRequest.getUniqueCode())
                .vat(invoicePositionRequest.getVat())
                .build();
        invoicePosition = calcPriceOnInvoicePosition(invoicePosition);
        calcPriceOnInvoice(invoice, invoicePosition);
        saveInvoicePosition(invoice, invoicePosition);
    }

    public InvoiceDto getInvoice(long id) throws InvoiceDoesNotExistException {
        Invoice invoice = findInvoice(id);
        return invoiceDtoMapper.map(invoice);
    }

    public Page<InvoiceProfileDto> findAllInvoices(Pageable pageable) {
        Page<Invoice> invoices = invoiceRepository.findAll(pageable);
        return invoices.map(invoice -> invoiceProfileDtoMapper.map(invoice));
    }

    public void payInvoice(long id) throws InvoiceDoesNotExistException {
        Invoice invoice = findInvoice(id);
        invoice.setPaid(true);
        invoice.setPaidAt(new Timestamp(System.currentTimeMillis()));
        saveInvoice(invoice);
    }

    public Page<InvoiceProfileDto> findUnpaidInvoices(Pageable pageable) {
        Page<Invoice> invoices = invoiceRepository.findInvoiceByPaid(false, pageable);
        return invoices.map(invoice -> invoiceProfileDtoMapper.map(invoice));
    }

    public byte[] generatePdfForInvoice(long id) throws InvoiceDoesNotExistException, IOException, DocumentException {
        Invoice invoice = findInvoice(id);
        return invoicePdfUtil.generatePdfForInvoice(invoice);
    }

    public void sendInvoiceViaEmail(String recipient, long invoiceId) throws InvoiceDoesNotExistException {
        Invoice invoice = findInvoice(invoiceId);
        RecipientList recipientList = new RecipientList();
        recipientList.addRecipient(recipient);
        try {
            mailSender.sendMessage(recipientList, "Hello World", "<div style=\"color: red;\">The mail's content</div>");
        } catch (MessagingException e) {
            e.printStackTrace();
        }
    }

    private long saveInvoice(Invoice invoice) {
        invoice.setIssuedAt(new Timestamp(System.currentTimeMillis()));
        invoiceRepository.save(invoice);
        log.info("persisted invoice with id [{}]", invoice.getId().longValue());
        return invoice.getId().longValue();
    }

    private void calcPriceOnInvoice(Invoice invoice, InvoicePosition invoicePosition) {
        invoice.setTotalPriceWithoutVat(invoice.getTotalPriceWithoutVat() + invoicePosition.getTotalPriceWithoutVat());
        invoice.setTotalPriceWithVat(invoice.getTotalPriceWithVat() + invoicePosition.getTotalPriceWithVat());
    }

    private InvoicePosition calcPriceOnInvoicePosition(InvoicePosition invoicePosition) {
        invoicePosition.setUnitPriceWithVat(invoicePosition.getUnitPrice() * ((invoicePosition.getVat() + 100.0)/100.0));
        invoicePosition.setTotalPriceWithoutVat(invoicePosition.getUnitPrice() * invoicePosition.getNum());
        invoicePosition.setTotalPriceWithVat(invoicePosition.getTotalPriceWithoutVat() * ((invoicePosition.getVat() + 100.0)/100.0));
        return invoicePosition;
    }

    private long saveInvoicePosition(Invoice invoice, InvoicePosition invoicePosition) {
        invoicePositionRepository.save(invoicePosition);
        log.info("persisted invoice positon with id [{}]", invoicePosition.getId().longValue());
        invoice.getPositions().add(invoicePosition);
        saveInvoice(invoice);
        return invoicePosition.getId().longValue();
    }

    private Invoice findInvoice(long id) throws InvoiceDoesNotExistException {
        Optional<Invoice> invoiceOptional = invoiceRepository.findInvoiceById(new Long(id));
        invoiceOptional.orElseThrow(() -> new InvoiceDoesNotExistException(id));
        return invoiceOptional.get();
    }

}
