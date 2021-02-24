package com.skyrocket.invoicer.repository;

import com.skyrocket.invoicer.model.Invoice;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public interface InvoiceRepository extends JpaRepository<Invoice, Long> {
    Optional<Invoice> findInvoiceById(Long id);
    Page<Invoice> findAll(Pageable pageable);
    Page<Invoice> findInvoiceByPaid(boolean paid, Pageable pageable);
}
