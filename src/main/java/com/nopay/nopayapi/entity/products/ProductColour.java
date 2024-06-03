package com.nopay.nopayapi.entity.products;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "product_colour")

public class ProductColour {

    /*
     * @Id
     * 
     * @GeneratedValue(strategy = GenerationType.IDENTITY)
     * 
     * @Column(name = "id_product_colour")
     * private Integer idProductColour;
     */

    @OneToOne
    @Column(table = "id_product")
    private Integer idProduct;

    @OneToOne
    @Column(table = "id_colour")
    private Integer idColour;

}
