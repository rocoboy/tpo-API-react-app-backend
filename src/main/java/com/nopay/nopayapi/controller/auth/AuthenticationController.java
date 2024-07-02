package com.nopay.nopayapi.controller.auth;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.nopay.nopayapi.service.AuthenticationService;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/auth")
@RequiredArgsConstructor
public class AuthenticationController {

    private final AuthenticationService service;

    @PostMapping("/register")
    public ResponseEntity<?> register(
            @RequestBody RegisterRequest request) {
        if (request.getFirstname() == null || request.getFirstname().isEmpty() || request.getLastname() == null
                || request.getLastname().isEmpty() || request.getEmail() == null || request.getEmail().isEmpty()
                || request.getPassword() == null || request.getPassword().isEmpty()) {
            return ResponseEntity.badRequest().body("Invalid request");
        }
        return ResponseEntity.ok(service.register(request));
    }

    @PostMapping("/authenticate")
    public ResponseEntity<?> authenticate(
            @RequestBody AuthenticationRequest request) {
        return ResponseEntity.ok(service.authenticate(request));
    }
}