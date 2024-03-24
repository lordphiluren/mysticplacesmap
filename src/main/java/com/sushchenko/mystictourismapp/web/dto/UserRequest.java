package com.sushchenko.mystictourismapp.web.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.databind.PropertyNamingStrategies;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import com.sushchenko.mystictourismapp.utils.validation.UpdateValidation;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.*;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonNaming(PropertyNamingStrategies.SnakeCaseStrategy.class)
public class UserRequest {
    @NotNull(message = "Username can not be empty", groups = {UpdateValidation.class})
    @Size(min = 4, max = 32, message = "Username size should be between 4 and 32")
    private String username;
    @Size(min = 2, max = 32, message = "Name size should be between 2 and 32")
    private String name;
    @Size(min = 2, max = 32, message = "Lastname size should be between 2 and 32")
    private String lastName;
    @Email(message = "Email should be valid")
    private String email;
}
