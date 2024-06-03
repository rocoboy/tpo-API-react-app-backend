package com.nopay.nopayapi.controller.users;

import com.nopay.nopayapi.entity.users.AccessUser;
import com.nopay.nopayapi.service.users.AccessUserService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/accessusers")
public class AccessUserController {

    @Autowired
    private AccessUserService accessUserService;

    @GetMapping
    public List<AccessUser> getAllUsers() {
        return accessUserService.findAll();
    }

    @GetMapping("/{id}")
    public ResponseEntity<AccessUser> getAccessUserById(@PathVariable Long id) {
        Optional<AccessUser> user = accessUserService.findById(id);
        return user.map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.notFound().build());
    }

    @PostMapping
    public AccessUser createAccessUser(@RequestBody AccessUser accessUser) {
        return accessUserService.save(accessUser);
    }

    @PutMapping("/{id}")
    public ResponseEntity<AccessUser> updateAccessUser(@PathVariable Long id,
            @RequestBody AccessUser accessUserDetails) {
        return ResponseEntity.notFound().build();
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteAccessUser(@PathVariable Long id) {
        accessUserService.deleteById(id);
        return ResponseEntity.noContent().build();
    }
}