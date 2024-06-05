package com.nopay.nopayapi.service.users;

import com.nopay.nopayapi.entity.users.AccessUser;
import com.nopay.nopayapi.repository.users.AccessUserRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class AccessUserService {

    @Autowired
    private AccessUserRepository accessUserRepositoryy;

    public List<AccessUser> findAll() {
        return accessUserRepositoryy.findAll();
    }

    public Optional<AccessUser> findById(Long id) {
        return accessUserRepositoryy.findById(id);
    }

    public AccessUser save(AccessUser accessUser) {
        return accessUserRepositoryy.save(accessUser);
    }

    public void deleteById(Long id) {
        accessUserRepositoryy.deleteById(id);
    }
}