package com.nopay.nopayapi.repository.invoice;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.nopay.nopayapi.entity.invoice.Invoice;

@Repository
public interface InvoiceRepository extends JpaRepository<Invoice, Long> {
}