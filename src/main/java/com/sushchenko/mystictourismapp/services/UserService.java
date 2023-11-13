package com.sushchenko.mystictourismapp.services;

import com.sushchenko.mystictourismapp.entities.Attachment;
import com.sushchenko.mystictourismapp.entities.User;
import com.sushchenko.mystictourismapp.repos.UserRepo;
import com.sushchenko.mystictourismapp.security.AuthenticationFacade;
import com.sushchenko.mystictourismapp.utils.exceptions.UserAlreadyExistException;
import com.sushchenko.mystictourismapp.utils.exceptions.UserNotFoundException;
import com.sushchenko.mystictourismapp.utils.filemanager.FileManager;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class UserService {
    private final UserRepo userRepo;
    private final AuthenticationFacade authFacade;
    private final FileManager fileManager;
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

    public User addUserProfilePicture(User user, MultipartFile file) {
        if(!file.isEmpty()) {
            String path = fileManager.createUsersDirectory(user.getUsername());
            String fileUrl = fileManager.saveFile(file, path);
            user.setProfilePicture(new Attachment(fileUrl));
        }
        return user;
    }
    public User getLoggedUserInfo() {
         return userRepo.findByUsername(authFacade.getAuthenticationPrincipal().getUsername()).orElse(null);
    }

}
