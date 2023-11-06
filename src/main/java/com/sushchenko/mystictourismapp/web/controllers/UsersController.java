package com.sushchenko.mystictourismapp.web.controllers;

import com.sushchenko.mystictourismapp.entities.User;
import com.sushchenko.mystictourismapp.services.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/users")
@RequiredArgsConstructor
public class UsersController {
    private final UserService userService;

    @GetMapping("")
    public List<User> getUsers() {
        return userService.getUsers();
    }
}
