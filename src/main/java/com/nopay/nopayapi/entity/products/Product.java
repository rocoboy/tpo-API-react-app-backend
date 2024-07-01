package com.nopay.nopayapi.entity.products;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.math.BigDecimal;
import java.util.HashSet;
import java.util.Set;
import com.nopay.nopayapi.entity.users.User;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "product")
public class Product {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_product")
    private Integer idProduct;

    private String description;
    private BigDecimal price;
    private Integer stock;

    @ManyToMany
    @JoinTable(name = "product_category", joinColumns = @JoinColumn(name = "product_id"), inverseJoinColumns = @JoinColumn(name = "category_id"))
    private Set<Category> categories = new HashSet<>();

    @ManyToOne
    @JoinColumn(name = "seller_id", nullable = false)
    private User seller;

}
