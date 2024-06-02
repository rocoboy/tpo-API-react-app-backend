package com.nopay.nopayapi.entity.products;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "product_category")

public class ProductCategory {

    // No primary ID here

    @Column(table = "id_product")
    private Integer idProduct;

    @Column(table = "id_category")
    private Integer idCategory;

}
