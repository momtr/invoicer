package com.skyrocket.invoicer.util;

import com.lowagie.text.Document;
import com.lowagie.text.DocumentException;
import com.lowagie.text.PageSize;
import com.lowagie.text.pdf.PdfWriter;
import com.skyrocket.invoicer.model.Invoice;
import org.springframework.stereotype.Component;
import org.thymeleaf.TemplateEngine;
import org.thymeleaf.context.Context;
import org.thymeleaf.templatemode.TemplateMode;
import org.thymeleaf.templateresolver.ClassLoaderTemplateResolver;
import org.xhtmlrenderer.pdf.ITextRenderer;

import java.io.*;

@Component
public class InvoicePdfUtil {

    public byte[] generatePdfForInvoice(Invoice invoice) throws IOException, DocumentException {
        String html = parseInvoiceTemplate(invoice);
        String outputFolder = System.getProperty("user.home") + File.separator + "Downloads" + File.separator + "invoice_" + invoice.getId().longValue() + ".pdf";
        FileOutputStream outputStream = new FileOutputStream(outputFolder);
        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
        ITextRenderer renderer = new ITextRenderer();
        renderer.setDocumentFromString(html);
        renderer.layout();
        renderer.createPDF(byteArrayOutputStream);
        outputStream.close();
        return byteArrayOutputStream.toByteArray();
    }

    private String parseInvoiceTemplate(Invoice invoice) {
        ClassLoaderTemplateResolver templateResolver = new ClassLoaderTemplateResolver();
        templateResolver.setSuffix(".html");
        templateResolver.setTemplateMode(TemplateMode.HTML);
        TemplateEngine templateEngine = new TemplateEngine();
        templateEngine.setTemplateResolver(templateResolver);
        Context context = getInvoiceContext(invoice);
        return templateEngine.process("invoice_template", context);
    }

    private Context getInvoiceContext(Invoice invoice) {
        Context context = new Context();
        context.setVariable("img", invoice.getIssuerLogo());
        context.setVariable("id", invoice.getId().longValue());
        context.setVariable("invoiceName", invoice.getInvoiceName());
        context.setVariable("issuedAt", invoice.getIssuedAt().toLocalDateTime().getDayOfMonth() + " / " + invoice.getIssuedAt().toLocalDateTime().getMonthValue() + " / " + invoice.getIssuedAt().toLocalDateTime().getYear());
        context.setVariable("issuerName", invoice.getIssuerName());
        context.setVariable("issuerEmail", invoice.getIssuerEmail());
        context.setVariable("issuerAddress", invoice.getIssuerAddress());
        context.setVariable("vatId", invoice.getVatId());
        context.setVariable("recName", invoice.getRecName());
        context.setVariable("recAddress", invoice.getRecAddress());
        context.setVariable("recEmail", invoice.getRecEmail());
        context.setVariable("payUntil", invoice.getPayUntil().toLocalDateTime().getDayOfMonth() + " / " + invoice.getPayUntil().toLocalDateTime().getMonthValue() + " / " + invoice.getPayUntil().toLocalDateTime().getYear());
        context.setVariable("skontoNote", invoice.getSkontoNote());
        context.setVariable("totalPriceWithoutVat", invoice.getTotalPriceWithoutVat());
        context.setVariable("totalPriceWithVat", invoice.getTotalPriceWithVat());
        context.setVariable("paid", invoice.isPaid() ? "PAID" : "NOT PAID");
        context.setVariable("vatTotal", invoice.getTotalPriceWithVat() - invoice.getTotalPriceWithoutVat());
        context.setVariable("issuerIban", invoice.getIssuerIban());
        context.setVariable("positions", invoice.getPositions());
        return context;
    }

}
