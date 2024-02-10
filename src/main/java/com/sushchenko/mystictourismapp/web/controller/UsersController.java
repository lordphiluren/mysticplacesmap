package com.sushchenko.mystictourismapp.web.controller;

import com.sushchenko.mystictourismapp.entity.User;
import com.sushchenko.mystictourismapp.service.UserService;
import com.sushchenko.mystictourismapp.utils.exception.ControllerErrorResponse;
import com.sushchenko.mystictourismapp.utils.mapper.UserMapper;
import com.sushchenko.mystictourismapp.web.dto.UserRequest;
import com.sushchenko.mystictourismapp.web.dto.UserResponse;
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

    @GetMapping("/{username}")
    public UserResponse getUserInfo(@PathVariable String username) {
        return userMapper.toDto(userService.getByUsername(username));
    }
    // TODO
    // add checking if user is updated user
    @PutMapping(path = "/{username}")
    public ResponseEntity<?> updateUserInfo(@PathVariable String username,
                                            @RequestPart UserRequest userDto) {
        User user = userMapper.toEntity(userDto);
        user.setId(userService.getByUsername(username).getId());
        userService.update(user);
        return ResponseEntity.ok("User info successfully updated");
    }

    // Exception Handling

    @ExceptionHandler
    private ResponseEntity<ControllerErrorResponse> handleException(RuntimeException e) {
        ControllerErrorResponse errorResponse = new ControllerErrorResponse(e.getMessage(),
                System.currentTimeMillis());
        ResponseStatus responseStatus = AnnotationUtils.findAnnotation(e.getClass(), ResponseStatus.class);
        HttpStatus httpStatus = responseStatus != null ? responseStatus.value() : HttpStatus.INTERNAL_SERVER_ERROR;
        return new ResponseEntity<>(errorResponse, httpStatus);
    }
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
