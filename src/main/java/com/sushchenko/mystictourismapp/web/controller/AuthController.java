package com.sushchenko.mystictourismapp.web.controller;

import com.sushchenko.mystictourismapp.entity.User;
import com.sushchenko.mystictourismapp.security.UserPrincipal;
import com.sushchenko.mystictourismapp.service.AuthService;
import com.sushchenko.mystictourismapp.utils.exception.ControllerErrorResponse;
import com.sushchenko.mystictourismapp.web.dto.AuthRequest;
import com.sushchenko.mystictourismapp.web.dto.AuthResponse;
import com.sushchenko.mystictourismapp.web.dto.UserResponse;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.core.annotation.AnnotationUtils;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/api/v1/auth")
@RequiredArgsConstructor
public class AuthController {
    private final AuthService authService;
    private final ModelMapper modelMapper;
    @PostMapping("/login")
    public AuthResponse login(@Valid @RequestBody AuthRequest authRequest) {
        return authService.attemptLogin(authRequest.getUsername(), authRequest.getPassword());
    }
    @PostMapping("/signup")
    public ResponseEntity<?> signUp(@Valid @RequestBody AuthRequest authRequest) {
        authService.signUp(modelMapper.map(authRequest, User.class));
        return ResponseEntity.ok("Successful registration");
    }
    @GetMapping("/me")
    public UserResponse getAuthUser(@AuthenticationPrincipal UserPrincipal userPrincipal) {
        return modelMapper.map(userPrincipal.getUser(), UserResponse.class);
    }

}
