package com.nopay.nopayapi.entity.products;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "product_material")

public class ProductMaterial {

    /*
     * @Id
     * 
     * @GeneratedValue(strategy = GenerationType.IDENTITY)
     * 
     * @Column(name = "id_product_material")
     * private Integer idProductMaterial;
     */

    @OneToOne
    @Column(table = "id_product")
    private Integer idProduct;

    // A Product can have more than one material?

    @OneToMany
    @Column(table = "id_material")
    private Integer idMaterial;

}
