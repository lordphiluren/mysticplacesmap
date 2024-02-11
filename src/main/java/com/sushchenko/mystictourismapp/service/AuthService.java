package com.sushchenko.mystictourismapp.service;

import com.mongodb.MongoWriteException;
import com.mongodb.WriteError;
import com.sushchenko.mystictourismapp.entity.User;
import com.sushchenko.mystictourismapp.entity.enums.Role;
import com.sushchenko.mystictourismapp.repo.UserRepo;
import com.sushchenko.mystictourismapp.security.JwtIssuer;
import com.sushchenko.mystictourismapp.security.UserPrincipal;
import com.sushchenko.mystictourismapp.utils.exception.UserAlreadyExistException;
import com.sushchenko.mystictourismapp.web.dto.AuthResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Objects;

@Service
@RequiredArgsConstructor
public class AuthService {
    private final JwtIssuer jwtIssuer;
    private final AuthenticationManager authenticationManager;
    private final UserRepo userRepo;
    private final PasswordEncoder bCryptPasswordEncoder;
    public AuthResponse attemptLogin(String username, String password) {
        Authentication auth = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(username, password)
        );
        SecurityContextHolder.getContext().setAuthentication(auth);
        UserPrincipal principal = (UserPrincipal) auth.getPrincipal();
        String token = jwtIssuer.issue(principal);
        return AuthResponse.builder()
                .token(token)
                .build();
    }
    @Transactional
    public void signUp(User user) {
        if(Objects.equals(user.getUsername(), "admin")) {
            user.setRole(Role.ROLE_ADMIN);
        } else {
            user.setRole(Role.ROLE_USER);
        }
        user.setPassword(bCryptPasswordEncoder.encode(user.getPassword()));
        userRepo.save(user);
    }
}
