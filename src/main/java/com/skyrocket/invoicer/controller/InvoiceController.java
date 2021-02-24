package com.skyrocket.invoicer.controller;

import com.lowagie.text.DocumentException;
import com.skyrocket.invoicer.dto.InvoiceDto;
import com.skyrocket.invoicer.dto.InvoiceProfileDto;
import com.skyrocket.invoicer.dto.requests.InvoicePositionRequest;
import com.skyrocket.invoicer.dto.requests.InvoiceRequest;
import com.skyrocket.invoicer.exception.InvoiceDoesNotExistException;
import com.skyrocket.invoicer.service.InvoiceService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.websocket.server.PathParam;
import java.io.IOException;

@RestController
@RequestMapping("/invoices")
@Slf4j
public class InvoiceController {

    @Autowired
    private InvoiceService invoiceService;

    private final int PAGE_SIZE = 10;

    @PostMapping("")
    public ResponseEntity<InvoiceDto> createInvoice(@RequestBody InvoiceRequest invoiceRequest) throws InvoiceDoesNotExistException {
        log.info("received request to create invoice");
        return ResponseEntity.ok(invoiceService.createInvoice(invoiceRequest));
    }

    @GetMapping("")
    public ResponseEntity<Page<InvoiceProfileDto>> findAllInvoices(@RequestParam(name = "page", defaultValue = "0") int page) {
        log.info("received request to fetch all invoices");
        Pageable pageable = PageRequest.of(page, PAGE_SIZE, Sort.by(Sort.Direction.DESC, "id"));
        return ResponseEntity.ok(invoiceService.findAllInvoices(pageable));
    }

    @GetMapping("/unpaid")
    public ResponseEntity<Page<InvoiceProfileDto>> findUnpaidInvoices(@RequestParam(name = "page", defaultValue = "0") int page) {
        log.info("received request to fetch all invoices");
        Pageable pageable = PageRequest.of(page, PAGE_SIZE, Sort.by(Sort.Direction.DESC, "id"));
        return ResponseEntity.ok(invoiceService.findUnpaidInvoices(pageable));
    }

    @PostMapping("/{invoiceId}/positions")
    public void addInvoicePosition(@PathVariable("invoiceId") long invoiceId, @RequestBody InvoicePositionRequest invoicePositionRequest) throws InvoiceDoesNotExistException {
        log.info("received request to add invoice position to invoice [{}]", invoiceId);
        invoiceService.addInvoicePosition(invoiceId, invoicePositionRequest);
    }

    @GetMapping("/{invoiceId}")
    public ResponseEntity<InvoiceDto> fetchInvoice(@PathVariable("invoiceId") long invoiceId) throws InvoiceDoesNotExistException {
        log.info("received request to fetch invoice [{}]", invoiceId);
        return ResponseEntity.ok(invoiceService.getInvoice(invoiceId));
    }

    @PostMapping("/{invoiceId}/pay")
    public void payInvoice(@PathVariable("invoiceId") long invoiceId) throws InvoiceDoesNotExistException {
        log.info("received request to pay invoice with id [{}]", invoiceId);
        invoiceService.payInvoice(invoiceId);
    }

    @GetMapping(value = "/{invoiceId}/download", produces = MediaType.APPLICATION_PDF_VALUE)
    public ResponseEntity<byte[]> downloadfInvoicePdf(@PathVariable("invoiceId") long invoiceId) throws DocumentException, InvoiceDoesNotExistException, IOException {
        log.info("received requrest to download invoice with id [{}]", invoiceId);
        return ResponseEntity.ok(invoiceService.generatePdfForInvoice(invoiceId));
    }

}
