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

    @Override
    public boolean equals(Object o) {
        if (this == o)
            return true;
        if (o == null || getClass() != o.getClass())
            return false;
        Size size = (Size) o;
        return idSize != null && idSize.equals(size.idSize);
    }

    @Override
    public int hashCode() {
        return idSize != null ? idSize.hashCode() : 0;
    }

}
