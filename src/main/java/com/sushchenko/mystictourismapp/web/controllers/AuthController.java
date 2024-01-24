package com.sushchenko.mystictourismapp.web.controllers;

import com.auth0.jwt.exceptions.JWTVerificationException;
import com.sushchenko.mystictourismapp.entities.User;
import com.sushchenko.mystictourismapp.security.UserPrincipal;
import com.sushchenko.mystictourismapp.services.AuthService;
import com.sushchenko.mystictourismapp.services.UserService;
import com.sushchenko.mystictourismapp.utils.exceptions.ControllerErrorResponse;
import com.sushchenko.mystictourismapp.web.dto.AuthRequest;
import com.sushchenko.mystictourismapp.web.dto.AuthResponse;
import com.sushchenko.mystictourismapp.web.dto.UserDTO;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/auth")
@RequiredArgsConstructor
public class AuthController {
    private final AuthService authService;
    private final ModelMapper modelMapper;
    private final UserService userService;
    @PostMapping("/login")
    public AuthResponse login(@RequestBody AuthRequest authRequest) {
        return authService.attemptLogin(authRequest.getUsername(), authRequest.getPassword());
    }
    @PostMapping("/signup")
    public ResponseEntity<?> signUp(@RequestBody AuthRequest authRequest) {
        authService.signUp(modelMapper.map(authRequest, User.class));
        return ResponseEntity.ok("Successful registration");
    }
    @GetMapping("/me")
    public UserDTO getAuthUser(@AuthenticationPrincipal UserPrincipal userPrincipal) {
        return modelMapper.map(userService.getById(userPrincipal.getUserId()), UserDTO.class);
    }

    @ExceptionHandler
    private ResponseEntity<ControllerErrorResponse> handleException(RuntimeException e) {
        ControllerErrorResponse errorResponse = new ControllerErrorResponse(
                e.getMessage(),
                System.currentTimeMillis());
        return new ResponseEntity<>(errorResponse, HttpStatus.BAD_REQUEST);
    }
    @ExceptionHandler
    private ResponseEntity<ControllerErrorResponse> handleException(JWTVerificationException e) {
        ControllerErrorResponse errorResponse = new ControllerErrorResponse(
                e.getMessage(),
                System.currentTimeMillis());
        return new ResponseEntity<>(errorResponse, HttpStatus.BAD_REQUEST);
    }
}
