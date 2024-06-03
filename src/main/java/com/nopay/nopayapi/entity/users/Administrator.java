package com.nopay.nopayapi.entity.users;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "administrator")
public class Administrator {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_administrator")
    private Integer idAdministrator;

    @Column(name = "id_user")
    private Integer idUser;

}
