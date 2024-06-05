package com.nopay.nopayapi.entity.products;

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
@Data
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "product_category")
public class ProductCategory {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_product_category")
    private Integer idProductCategory;

    @Column(name = "id_product", nullable = false)
    private Integer idProduct;

    @Column(name = "id_category", nullable = false)
    private Integer idCategory;
}
