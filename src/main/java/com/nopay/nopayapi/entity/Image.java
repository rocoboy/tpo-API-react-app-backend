package com.nopay.nopayapi.entity;

import jakarta.persistence.*;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Data
@Entity
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "image")
public class Image {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "image")
    private byte[] imageBytes;

    @Column(name = "name")
    private byte[] name;

    @Column(name = "date")
    private Date date;

    public byte[] getImage() {
        return this.imageBytes;
    }
}
