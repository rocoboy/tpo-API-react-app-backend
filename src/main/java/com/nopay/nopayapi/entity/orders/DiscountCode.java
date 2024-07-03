package com.nopay.nopayapi.entity.orders;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

import com.nopay.nopayapi.entity.users.User;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "discount_codes")
public class DiscountCode {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_discount_code")
    private Integer id;

    @Column(name = "code", unique = true, nullable = false)
    private String code;

    @Column(name = "discount_amount")
    private BigDecimal discountAmount;

    @Column(name = "is_whole_order")
    private Boolean isWholeOrder;

    @Column(name = "description")
    private String description;

    private Boolean active;

    // lets link this code to the seller who created it
    @ManyToOne
    @JoinColumn(name = "seller_id")
    private User seller;
}
