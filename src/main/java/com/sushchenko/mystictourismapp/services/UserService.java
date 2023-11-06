package com.sushchenko.mystictourismapp.services;

import com.sushchenko.mystictourismapp.entities.User;
import com.sushchenko.mystictourismapp.repos.UserRepo;
import com.sushchenko.mystictourismapp.utils.exceptions.UserAlreadyExistException;
import com.sushchenko.mystictourismapp.utils.exceptions.UserNotFoundException;
import lombok.AllArgsConstructor;
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
        return userRepo.findById(id).orElseThrow(() -> new UserNotFoundException("User with this id was not found"));
    }
    @Transactional
    public User getByUsername(String username) {
        return userRepo.findByUsername(username).orElseThrow(() -> new UserNotFoundException("User with this id was not found"));
    }
    @Transactional
    public void add(User user) {
        if (userRepo.findByUsername(user.getUsername()).isEmpty())
            userRepo.save(user);
        else throw new UserAlreadyExistException("Username taken");
    }

}
