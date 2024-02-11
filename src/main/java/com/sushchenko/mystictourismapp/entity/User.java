package com.sushchenko.mystictourismapp.entity;

import com.sushchenko.mystictourismapp.entity.enums.Role;
import jakarta.persistence.Id;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.*;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.Document;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Document(collection = "users")
public class User {
    @Id
    private String id;
    @NotNull(message = "Username can not be empty")
    @Size(min = 4, max = 32, message = "Username size should be between 4 and 32")
    private String username;
    @Size(min = 6, max = 20, message = "Password size should be between 6 and 20")
    @NotNull(message = "Password can not be empty")
    private String password;
    @Size(min = 2, max = 32, message = "Name size should be between 2 and 32")
    private String name;
    @Size(min = 2, max = 32, message = "Lastname size should be between 2 and 32")
    private String lastName;
    @Email(message = "Email should be valid")
    private String email;
    private Attachment profilePicture;
    @NotNull(message = "Role cannot be empty")
    private Role role;
}
