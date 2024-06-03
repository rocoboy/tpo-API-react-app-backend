package com.nopay.nopayapi.controller.users;

import com.nopay.nopayapi.entity.users.Administrator;
import com.nopay.nopayapi.service.users.AdministratorService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/administrators")
public class AdministratorController {

    @Autowired
    private AdministratorService administratorService;

    @GetMapping
    public List<Administrator> getAllAdministrators() {
        return administratorService.findAll();
    }

    @GetMapping("/{id}")
    public ResponseEntity<Administrator> getAdministratorById(@PathVariable Long id) {
        Optional<Administrator> administrator = administratorService.findById(id);
        return administrator.map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.notFound().build());
    }

    @PostMapping
    public Administrator createAdministrator(@RequestBody Administrator administrator) {
        return administratorService.save(administrator);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Administrator> updateAdministrator(@PathVariable Long id,
            @RequestBody Administrator administratorDetails) {
        Optional<Administrator> administrator = administratorService.findById(id);
        if (administrator.isPresent()) {
            Administrator updatedAdministrator = administrator.get();
            updatedAdministrator.setIdAdministrator(administratorDetails.getIdAdministrator());
        } else {
            return ResponseEntity.notFound().build();
        }
        return null;
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteUser(@PathVariable Long id) {
        administratorService.deleteById(id);
        return ResponseEntity.noContent().build();
    }
}