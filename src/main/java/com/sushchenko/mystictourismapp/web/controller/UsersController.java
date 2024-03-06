package com.sushchenko.mystictourismapp.web.controller;

import com.sushchenko.mystictourismapp.entity.User;
import com.sushchenko.mystictourismapp.service.UserService;
import com.sushchenko.mystictourismapp.utils.exception.ControllerErrorResponse;
import com.sushchenko.mystictourismapp.utils.mapper.UserMapper;
import com.sushchenko.mystictourismapp.web.dto.UserRequest;
import com.sushchenko.mystictourismapp.web.dto.UserResponse;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.core.annotation.AnnotationUtils;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/api/users")
@RequiredArgsConstructor
public class UsersController {
    private final UserService userService;
    private final UserMapper userMapper;
    @GetMapping("/{id}")
    public UserResponse getUserInfo(@PathVariable Long id) {
        return userMapper.toDto(userService.getById(id));
    }
    // TODO
    // add checking if user is updated user
    @PutMapping(path = "/{id}")
    public ResponseEntity<?> updateUserInfo(@PathVariable Long id,
                                            @Valid @RequestBody UserRequest userDto) {
        User user = userMapper.toEntity(userDto);
        user.setId(id);
        userService.update(user);
        return ResponseEntity.ok("User info successfully updated");
    }
}
