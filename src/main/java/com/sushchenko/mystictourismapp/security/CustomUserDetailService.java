package com.sushchenko.mystictourismapp.security;

import com.sushchenko.mystictourismapp.entity.User;
import com.sushchenko.mystictourismapp.repo.UserRepo;
import com.sushchenko.mystictourismapp.utils.exception.UserNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class CustomUserDetailService implements UserDetailsService {
    private final UserRepo userRepo;
    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        User user = userRepo.findByUsername(username).orElseThrow(() -> new UserNotFoundException("User doesn't exist"));
        return UserPrincipal.builder()
                .user(user)
                .build();
    }
}
