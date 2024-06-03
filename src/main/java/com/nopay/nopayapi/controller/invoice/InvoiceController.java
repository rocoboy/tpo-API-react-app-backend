package com.nopay.nopayapi.controller.invoice;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.nopay.nopayapi.entity.invoice.Invoice;
import com.nopay.nopayapi.service.invoice.InvoiceService;

@RestController
@RequestMapping("/invoice")
public class InvoiceController {

    @Autowired
    private InvoiceService invoiceService;

    @GetMapping
    public List<Invoice> getAllInvoices() {
        return invoiceService.findAll();
    }

    @GetMapping("/{id}")
    public ResponseEntity<Invoice> getInvoiceByID(@PathVariable Long id) {
        Optional<Invoice> user = invoiceService.findById(id);
        return user.map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.notFound().build());
    }

    @PostMapping
    public Invoice uploadInvoice(@RequestBody Invoice invoice) {
        return invoiceService.save(invoice);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Invoice> updateInvoice(@PathVariable Long id, @RequestBody Invoice invoiceDetails) {
        Optional<Invoice> invoice = invoiceService.findById(id);
        if (invoice.isPresent()) {
            Invoice updatedInvoice = invoice.get();
            updatedInvoice.setId_client(invoiceDetails.getId_client());
            updatedInvoice.setDate(invoiceDetails.getDate());
            updatedInvoice.setDetailType(invoiceDetails.getDetailType());
            updatedInvoice.setCardIssuer(invoiceDetails.getCardIssuer());
            updatedInvoice.setDues(invoiceDetails.getDues());
            updatedInvoice.setInterest(invoiceDetails.getInterest());
            return ResponseEntity.ok(invoiceService.save(updatedInvoice));
        } else {
            return ResponseEntity.notFound().build();
        }
    }
    
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteInvoice(@PathVariable Long id) {
        invoiceService.deleteById(id);
        return ResponseEntity.noContent().build();
    }
}