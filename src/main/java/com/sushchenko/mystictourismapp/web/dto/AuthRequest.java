package com.sushchenko.mystictourismapp.web.dto;

import lombok.Builder;
import lombok.Getter;

@Builder
@Getter
public class AuthRequest {
    private String username;
    private String password;
}
