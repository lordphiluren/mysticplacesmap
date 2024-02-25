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
    public User getById(Long id) {
        return userRepo.findById(id)
                .orElseThrow(() -> new UserNotFoundException("User with id:" + id + " was not found"));
    }
    @Transactional
    public User getByUsername(String username) {
        return userRepo.findByUsername(username)
                .orElseThrow(() -> new UserNotFoundException("User with username:" +  username + " was not found"));
    }
    @Transactional
    public void update(User user) {
        userRepo.save(user);
    }
}
