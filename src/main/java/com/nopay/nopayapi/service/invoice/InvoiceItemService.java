package com.nopay.nopayapi.service.invoice;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.nopay.nopayapi.entity.invoice.InvoiceItem;
import com.nopay.nopayapi.repository.invoice.InvoiceItemRepository;

@Service
public class InvoiceItemService {

    @Autowired
    private InvoiceItemRepository invoiceItemRepository;

    public List<InvoiceItem> findAll() {
        return invoiceItemRepository.findAll();
    }

    public Optional<InvoiceItem> findById(Integer id) {
        return invoiceItemRepository.findById(id);
    }

    public InvoiceItem save(InvoiceItem invoiceItem) {
        return invoiceItemRepository.save(invoiceItem);
    }

    public void deleteById(Integer id) {
        invoiceItemRepository.deleteById(id);
    }
}
