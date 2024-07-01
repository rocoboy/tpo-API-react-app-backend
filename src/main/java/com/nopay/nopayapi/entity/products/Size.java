package com.nopay.nopayapi.entity.products;

import java.io.Serializable;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "size")
public class Size implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_size")
    private Integer idSize;

    @Enumerated(EnumType.STRING)
    private AcceptedSizes description;

    private Integer stock;

    @ManyToOne
    @JoinColumn(name = "id_product", nullable = false)
    private Product product;

}
