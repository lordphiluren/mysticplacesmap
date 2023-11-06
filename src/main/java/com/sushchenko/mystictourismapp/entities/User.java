package com.sushchenko.mystictourismapp.entities;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.sushchenko.mystictourismapp.entities.enums.Role;
import jakarta.persistence.Id;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotNull;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.Document;

@Data
@NoArgsConstructor
@Document(collection = "users")
public class User {
    @Id
    private String id;
    @NotNull(message = "Username cannot be null")
    private String username;
    @JsonIgnore
    @NotNull(message = "Password cannot be null")
    private String password;
    private String name;
    private String lastName;
    @Email(message = "Email should be valid")
    private String email;
    @NotNull(message = "Role cannot be null")
    private Role role;
}
