package com.skyrocket.invoicer.repository;

import com.skyrocket.invoicer.model.InvoicePosition;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Service;

@Service
public interface InvoicePositionRepository extends JpaRepository<InvoicePosition, Long> {
}
