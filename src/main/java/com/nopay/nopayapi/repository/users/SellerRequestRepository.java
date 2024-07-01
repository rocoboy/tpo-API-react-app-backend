package com.nopay.nopayapi.repository.users;

import com.nopay.nopayapi.entity.users.SellerRequest;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface SellerRequestRepository extends JpaRepository<SellerRequest, Integer> {
}