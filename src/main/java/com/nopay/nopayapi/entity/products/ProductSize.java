package com.nopay.nopayapi.entity.products;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "product_size")

public class ProductSize {

    /*
     * @Id
     * 
     * @GeneratedValue(strategy = GenerationType.IDENTITY)
     * 
     * @Column(name = "id_product_size")
     * private Integer idProductMaterial;
     * 
     */

    @OneToOne
    @Column(name = "id_product")
    private Integer idProduct;

    @OneToOne
    @Column(name = "id_size")
    private Integer idSize;

}
