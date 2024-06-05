package com.nopay.nopayapi.entity.invoice;

import java.math.BigDecimal;
import java.time.LocalDate;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Table(name = "invoice")
@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Invoice {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_invoice")
    private Integer idInvoice;

    @Column(name = "id_client")
    private Integer idClient;

    private LocalDate date;

    @Column(name = "detail_type")
    private String detailType;

    @Column(name = "card_issuer")
    private String cardIssuer;

    private BigDecimal dues;

    private BigDecimal interest;
}
