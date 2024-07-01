package com.nopay.nopayapi.entity.users;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "seller_request")
public class SellerRequest {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_request")
    private Integer idRequest;

    @ManyToOne
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    @Enumerated(EnumType.STRING)
    private RequestStatus status = RequestStatus.PENDING;

    public enum RequestStatus {
        PENDING,
        APPROVED,
        REJECTED
    }
}
