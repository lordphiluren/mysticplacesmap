package com.sushchenko.mystictourismapp.service;

import com.sushchenko.mystictourismapp.entity.User;
import com.sushchenko.mystictourismapp.repo.UserRepo;
import com.sushchenko.mystictourismapp.utils.exception.UserAlreadyExistException;
import com.sushchenko.mystictourismapp.utils.exception.UserNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class UserService {
    private final UserRepo userRepo;
    @Transactional
    public List<User> getUsers() {
        return userRepo.findAll();
    }
    @Transactional
    public User getById(String id) {
        return userRepo.findById(id)
                .orElseThrow(() -> new UserNotFoundException("User with id:" + id + " was not found"));
    }
    @Transactional
    public User getByUsername(String username) {
        return userRepo.findByUsername(username)
                .orElseThrow(() -> new UserNotFoundException("User with username:" +  username + " was not found"));
    }
    @Transactional
    public void add(User user) {
        if (userRepo.findByUsername(user.getUsername()).isEmpty())
            userRepo.save(user);
        else throw new UserAlreadyExistException("Username is already taken");
    }
    @Transactional
    public void update(User user) {
        userRepo.save(user);
    }

//    public void addUserProfilePicture(User user, MultipartFile file) {
//        if(!file.isEmpty()) {
//            String path = fileManager.createDirectory(user.getUsername());
//            String fileUrl = fileManager.saveFile(file, path);
//            user.setProfilePicture(new Attachment(fileUrl));
//        }
//    }
}
