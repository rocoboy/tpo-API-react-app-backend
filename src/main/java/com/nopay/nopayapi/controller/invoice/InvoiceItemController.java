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

import com.nopay.nopayapi.entity.invoice.InvoiceItem;
import com.nopay.nopayapi.service.invoice.InvoiceItemService;

@RestController
@RequestMapping("/item_invoice")
public class InvoiceItemController {

    @Autowired
    private InvoiceItemService invoiceItemService;

    @GetMapping
    public List<InvoiceItem> getAllInvoiceItems() {
        return invoiceItemService.findAll();
    }

    @GetMapping("/{id}")
    public ResponseEntity<InvoiceItem> getInvoiceItemByID(@PathVariable Long id) {
        Optional<InvoiceItem> user = invoiceItemService.findById(id);
        return user.map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.notFound().build());
    }

    @PostMapping
    public InvoiceItem uploadInvoiceItem(@RequestBody InvoiceItem invoiceItem) {
        return invoiceItemService.save(invoiceItem);
    }

    @PutMapping("/{id}")
    public ResponseEntity<InvoiceItem> updateInvoiceItem(@PathVariable Long id, @RequestBody InvoiceItem invoiceItemDetails) {
        Optional<InvoiceItem> invoice = invoiceItemService.findById(id);
        if (invoice.isPresent()) {
            InvoiceItem updatedInvoiceItem = invoice.get();
            updatedInvoiceItem.setId_invoice(invoiceItemDetails.getId_invoice());
            updatedInvoiceItem.setId_product(invoiceItemDetails.getId_product());
            updatedInvoiceItem.setPrice(invoiceItemDetails.getPrice());
            updatedInvoiceItem.setQuantity(invoiceItemDetails.getQuantity());
            return ResponseEntity.ok(invoiceItemService.save(updatedInvoiceItem));
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteInvoiceItemByID(@PathVariable Long id) {
        invoiceItemService.deleteById(id);
        return ResponseEntity.noContent().build();
    }
}