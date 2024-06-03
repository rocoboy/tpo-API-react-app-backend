package com.nopay.nopayapi.entity.invoice;

import java.math.BigDecimal;
import java.time.LocalDate;

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

    private Integer id_invoice;

    private Integer id_client;


    private LocalDate date;


    private String detailType;


    private String cardIssuer;


    private BigDecimal dues;

    private BigDecimal interest;

}
