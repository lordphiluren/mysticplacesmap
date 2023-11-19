package com.sushchenko.mystictourismapp.web.controllers;

import com.sushchenko.mystictourismapp.entities.User;
import com.sushchenko.mystictourismapp.services.UserService;
import com.sushchenko.mystictourismapp.utils.exceptions.ControllerErrorResponse;
import com.sushchenko.mystictourismapp.utils.mappers.UserMapper;
import com.sushchenko.mystictourismapp.web.dto.UserDTO;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import static org.springframework.web.bind.annotation.RequestMethod.PUT;

@RestController
@RequestMapping("/api/users")
@RequiredArgsConstructor
public class UsersController {
    private final UserService userService;
    private final UserMapper userMapper;

    @GetMapping("/{username}")
    public UserDTO getUserInfo(@PathVariable String username) {
        return userMapper.mapToUserDTO(userService.getByUsername(username));
    }
    @RequestMapping(path = "/{username}", method = PUT, consumes = { MediaType.MULTIPART_FORM_DATA_VALUE })
    public ResponseEntity<?> updateUserInfo(@PathVariable String username,
                                            @RequestPart UserDTO userDTO,
                                            @RequestPart(required = false) MultipartFile file) {
        User user = userMapper.mapToUser(userDTO);
        user.setId(userService.getByUsername(username).getId());
        userService.addUserProfilePicture(user, file);
        userService.update(user);
        return ResponseEntity.ok("User info successfully updated");
    }

    @ExceptionHandler
    private ResponseEntity<ControllerErrorResponse> handleException(RuntimeException e) {
        ControllerErrorResponse errorResponse = new ControllerErrorResponse(
                e.getMessage(),
                System.currentTimeMillis());
        return new ResponseEntity<>(errorResponse, HttpStatus.BAD_REQUEST);
    }
}
