package com.sushchenko.mystictourismapp.web.dto;

import com.sushchenko.mystictourismapp.entities.enums.Role;
import jakarta.validation.constraints.Email;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class UserDTO {
    private String id;
    private String username;
    private String name;
    private String lastName;
    @Email(message = "Email should be valid")
    private String email;
    private Role role;
}
