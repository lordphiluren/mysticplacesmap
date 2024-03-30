package com.sushchenko.mystictourismapp.service;

import com.sushchenko.mystictourismapp.entity.User;
import com.sushchenko.mystictourismapp.repo.UserRepo;
import com.sushchenko.mystictourismapp.service.helper.Helper;
import com.sushchenko.mystictourismapp.utils.exception.NotEnoughPermissionsException;
import com.sushchenko.mystictourismapp.utils.exception.UserAlreadyExistException;
import com.sushchenko.mystictourismapp.utils.exception.UserNotFoundException;
import com.sushchenko.mystictourismapp.utils.mapper.UserMapper;
import com.sushchenko.mystictourismapp.web.dto.UserRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;
import java.util.Objects;

@Service
@RequiredArgsConstructor
public class UserService {
    private final UserRepo userRepo;
    private final UserMapper userMapper;
    private final UploadService uploadService;

    @Transactional
    public User getById(Long id) {
        return userRepo.findById(id)
                .orElseThrow(() -> new UserNotFoundException("User with id:" + id + " was not found"));
    }
    @Transactional
    public User update(Long id, UserRequest userDto, MultipartFile attachment, User creator) {
        User user = getById(id);
        userMapper.mergeDtoIntoEntity(userDto, user);
        if(Helper.checkUserPermissions(user, creator)) {
            user = userRepo.save(user);
            user.setProfilePicture(uploadService.uploadAttachment(attachment));
        } else {
            throw new NotEnoughPermissionsException("User with id: " + creator.getId() +
                    " can't modify user with id: " + id);
        }
        return user;
    }
}
