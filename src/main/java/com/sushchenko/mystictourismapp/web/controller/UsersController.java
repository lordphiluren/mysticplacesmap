package com.sushchenko.mystictourismapp.web.controller;

import com.sushchenko.mystictourismapp.security.UserPrincipal;
import com.sushchenko.mystictourismapp.service.UserService;
import com.sushchenko.mystictourismapp.utils.mapper.UserMapper;
import com.sushchenko.mystictourismapp.utils.validation.UpdateValidation;
import com.sushchenko.mystictourismapp.web.dto.UserRequest;
import com.sushchenko.mystictourismapp.web.dto.UserResponse;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;


@RestController
@RequestMapping("/api/v1/users")
@RequiredArgsConstructor
@Tag(name = "Users", description = "User account manipulations")
public class UsersController {
    private final UserService userService;
    private final UserMapper userMapper;
    @Operation(
            summary = "Get user by id"
    )
    @GetMapping("/{id}")
    public UserResponse getUserInfo(@PathVariable Long id) {
        return userMapper.toDto(userService.getById(id));
    }
    @Operation(
            summary = "Update user info by id"
    )
    @SecurityRequirement(name = "JWT")
    @PutMapping(path = "/{id}")
    public ResponseEntity<?> updateUserInfo(@PathVariable Long id,
                                            @Validated(UpdateValidation.class) @RequestBody UserRequest userDto,
                                            @AuthenticationPrincipal UserPrincipal userPrincipal) {
        return ResponseEntity.ok(
            userMapper.toDto(userService.update(id, userDto, userPrincipal.getUser()))
        );
    }
}
