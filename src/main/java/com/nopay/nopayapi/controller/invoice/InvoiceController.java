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
import com.nopay.nopayapi.service.users.UserService;

@RestController
@RequestMapping("/invoice")
public class InvoiceController {

    @Autowired
    private InvoiceService invoiceService;

    @Autowired
    private UserService userService;

    @GetMapping
    public List<Invoice> getAllInvoices() {
        return invoiceService.findAll();
    }

    @GetMapping("/{id}")
    public ResponseEntity<Invoice> getInvoiceByID(@PathVariable Integer id) {
        Optional<Invoice> user = invoiceService.findById(id);
        return user.map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.notFound().build());
    }

    @PostMapping
    public ResponseEntity<?> uploadInvoice(@RequestBody Invoice invoice) {
        Integer idClient = Integer.valueOf(invoice.getIdClient());
        if (!userService.findById(idClient).isPresent()) {
            return ResponseEntity.badRequest().body("The User does not exist");
        }
        return ResponseEntity.ok(invoiceService.save(invoice));
    }

    @PutMapping("/{id}")
    public ResponseEntity<?> updateInvoice(@PathVariable Integer id, @RequestBody Invoice invoiceDetails) {
        Optional<Invoice> invoice = invoiceService.findById(id);
        if (invoice.isPresent()) {

            if (invoiceDetails.getIdClient() != null) {
                Integer idClient = Integer.valueOf(invoiceDetails.getIdClient());
                if (!userService.findById(idClient).isPresent()) {
                    return ResponseEntity.badRequest().body("The User does not exist");
                }
            }
            Invoice updatedInvoice = invoice.get();

            if (invoiceDetails.getIdClient() != null) {
                updatedInvoice.setIdClient(invoiceDetails.getIdClient());
            }
            if (invoiceDetails.getDate() != null) {
                updatedInvoice.setDate(invoiceDetails.getDate());
            }
            if (invoiceDetails.getDetailType() != null) {
                updatedInvoice.setDetailType(invoiceDetails.getDetailType());
            }
            if (invoiceDetails.getCardIssuer() != null) {
                updatedInvoice.setCardIssuer(invoiceDetails.getCardIssuer());
            }
            if (invoiceDetails.getDues() != null) {
                updatedInvoice.setDues(invoiceDetails.getDues());
            }
            if (invoiceDetails.getInterest() != null) {
                updatedInvoice.setInterest(invoiceDetails.getInterest());
            }

            return ResponseEntity.ok(invoiceService.save(updatedInvoice));
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteInvoice(@PathVariable Integer id) {
        invoiceService.deleteById(id);
        return ResponseEntity.noContent().build();
    }
}