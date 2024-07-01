package com.nopay.nopayapi.entity.orders;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "order_discounts")
public class OrderDiscount {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_discount")
    private Integer idDiscount;

    @ManyToOne
    @JoinColumn(name = "order_id", nullable = false)
    private Order order;

    private BigDecimal discountAmount;
    private String description;
}
