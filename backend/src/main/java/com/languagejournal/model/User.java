package com.languagejournal.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.*;

import java.util.UUID;

@Entity
@Table(name = "users")
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    @NotBlank(message = "Name cannot be blank!")
    @Size(min = 2, max = 100, message = "Name must be between between 2 and 100 characters.")
    @Column(nullable = false, length = 100)
    private String name;

    @NotBlank(message = "Email cannot be blank!")
    @Email(message = "Email must be valid.")
    @Column(nullable = false, unique = true, length = 100)
    private String email;

}
