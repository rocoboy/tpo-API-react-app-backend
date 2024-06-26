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

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_product_material")
    private Integer idProductMaterial;

    @ManyToOne
    @JoinColumn(name = "id_product", referencedColumnName = "id_product")
    private Product product;

    @ManyToOne
    @JoinColumn(name = "id_material", referencedColumnName = "id_material")
    private Material material;
}