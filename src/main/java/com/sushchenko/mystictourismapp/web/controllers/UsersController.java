package com.sushchenko.mystictourismapp.web.controllers;

import com.sushchenko.mystictourismapp.services.UserService;
import com.sushchenko.mystictourismapp.utils.mappers.UserMapper;
import com.sushchenko.mystictourismapp.web.dto.UserDTO;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/users")
@RequiredArgsConstructor
public class UsersController {
    private final UserService userService;
    private final UserMapper userMapper;

    @GetMapping("")
    public UserDTO getUserInfo() {
        return userMapper.mapToUserDTO(userService.getLoggedUserInfo());
    }
}
