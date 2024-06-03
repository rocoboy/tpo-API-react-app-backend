package com.nopay.nopayapi.entity.users;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "access_user")
public class AccessUser {
    @Id
    @Column(name = "id_access_user")
    private Integer idAccessUser;

    @Column(name = "key")
    private String accessKey;
}
