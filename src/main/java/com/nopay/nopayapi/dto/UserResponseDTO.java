package com.nopay.nopayapi.dto;

import java.util.Optional;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class UserResponseDTO {

    private Integer id;
    private String firstName;
    private String lastName;
    private String email;
    private Optional<String> phone;
    private Optional<String> city;
    private Optional<String> address;
    private Optional<String> postalCode;
    private String role;
    private String dni;

    public String getFullName() {
        return firstName + " " + lastName;
    }

}
