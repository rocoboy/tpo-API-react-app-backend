package com.nopay.nopayapi.entity.products;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "product_seller")

public class ProductSeller {

    /*
     * @Id
     * 
     * @GeneratedValue(strategy = GenerationType.IDENTITY)
     * 
     * @Column(name = "id_product_seller")
     * private Integer idProductMaterial;
     * 
     */

    // Assuming each product is unique to each seller

    @OneToOne
    @Column(table = "id_product")
    private Integer idProduct;

    @OneToOne
    @Column(table = "id_seller")
    private Integer idSeller;
}
