package com.nopay.nopayapi.entity;

import jakarta.persistence.*;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

import com.nopay.nopayapi.entity.products.Product;

@Data
@Entity
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "image")
public class Image {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(name = "image")
    private byte[] imageBytes;

    @Column(name = "date")
    private Date date;

    @ManyToOne
    @JoinColumn(name = "product_id")
    private Product product;

}
