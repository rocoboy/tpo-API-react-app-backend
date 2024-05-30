package com.nopay.nopayapi.repository.users;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.nopay.nopayapi.entity.users.Administrator;

@Repository
public interface AdministratorRepository extends JpaRepository<Administrator, Long> {
    ///
}