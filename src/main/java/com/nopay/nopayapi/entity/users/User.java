package com.nopay.nopayapi.entity.users;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "user_account")
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)

    @Column(name = "id_user")
    private Integer idUser;

    @Column(name = "postal_code")
    private String postalCode;

    private int dni;
    private String name;
    private String surname;
    private String address;
    private String city;
    private String phone;
}