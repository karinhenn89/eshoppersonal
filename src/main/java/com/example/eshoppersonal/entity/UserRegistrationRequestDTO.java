package com.example.eshoppersonal.entity;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class UserRegistrationRequestDTO {
    private String email;
    private String password;
    private Role role; // Kasutaja valib rolli
}
