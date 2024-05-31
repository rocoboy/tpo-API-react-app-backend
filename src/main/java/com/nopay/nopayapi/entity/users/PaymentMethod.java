package com.nopay.nopayapi.entity.users;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "payment_methods")
public class PaymentMethod {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_payment_methods")
    private Integer idPaymentMethod;

    @Column(name = "detail_type")
    private String detailType;

    @Column(name = "card_issuer")
    private String cardIusuer;

    @Column(name = "id_user")
    private Integer idUser;

    private Float dues;
    private Float interest;
}