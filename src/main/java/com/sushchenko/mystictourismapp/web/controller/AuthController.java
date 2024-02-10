package com.sushchenko.mystictourismapp.web.controller;

import com.sushchenko.mystictourismapp.entity.User;
import com.sushchenko.mystictourismapp.security.UserPrincipal;
import com.sushchenko.mystictourismapp.service.AuthService;
import com.sushchenko.mystictourismapp.service.UserService;
import com.sushchenko.mystictourismapp.web.dto.AuthRequest;
import com.sushchenko.mystictourismapp.web.dto.AuthResponse;
import com.sushchenko.mystictourismapp.web.dto.UserResponse;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;

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
    public UserResponse getAuthUser(@AuthenticationPrincipal UserPrincipal userPrincipal) {
        return modelMapper.map(userPrincipal.getUser(), UserResponse.class);
    }


    // Exception Handling

//    @ExceptionHandler
//    private ResponseEntity<ControllerErrorResponse> handleException(RuntimeException e) {
//        ControllerErrorResponse errorResponse = new ControllerErrorResponse(e.getMessage(),
//                System.currentTimeMillis());
//        ResponseStatus responseStatus = AnnotationUtils.findAnnotation(e.getClass(), ResponseStatus.class);
//        HttpStatus httpStatus = responseStatus != null ? responseStatus.value() : HttpStatus.INTERNAL_SERVER_ERROR;
//        return new ResponseEntity<>(errorResponse, httpStatus);
//    }
    // validation exception handler
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public Map<String, String> handleValidationExceptions(
            MethodArgumentNotValidException ex) {
        Map<String, String> errors = new HashMap<>();
        ex.getBindingResult().getAllErrors().forEach((error) -> {
            String fieldName = ((FieldError) error).getField();
            String errorMessage = error.getDefaultMessage();
            errors.put(fieldName, errorMessage);
        });
        return errors;
    }
}
