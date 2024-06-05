package com.nopay.nopayapi.entity.invoice;

import java.math.BigDecimal;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "item_invoice")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class InvoiceItem {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_item_invoice")
    private Integer idItemInvoice;

    @Column(name = "id_invoice")
    private Integer idInvoice;

    @Column(name = "id_product")
    private Integer idProduct;

    private Integer quantity;
    private BigDecimal price;
}
