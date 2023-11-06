package com.sushchenko.mystictourismapp.services;

import com.sushchenko.mystictourismapp.entities.User;
import com.sushchenko.mystictourismapp.entities.enums.Role;
import com.sushchenko.mystictourismapp.security.JwtIssuer;
import com.sushchenko.mystictourismapp.security.UserPrincipal;
import com.sushchenko.mystictourismapp.web.dto.AuthResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class AuthService {
    private final JwtIssuer jwtIssuer;
    private final AuthenticationManager authenticationManager;
    private final UserService userService;
    private final PasswordEncoder bCryptPasswordEncoder;
    public AuthResponse attemptLogin(String username, String password) {
        Authentication auth = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(username, password)
        );
        SecurityContextHolder.getContext().setAuthentication(auth);
        UserPrincipal principal = (UserPrincipal) auth.getPrincipal();
        var authorities = principal.getAuthorities()
                .stream()
                .map(GrantedAuthority::getAuthority).toList();
        String token = jwtIssuer.issue(principal.getUserId(), principal.getUsername(), authorities);
        return AuthResponse.builder()
                .token(token)
                .build();
    }
    @Transactional
    public void signUp(User user) {
        user.setRole(Role.ROLE_USER);
        user.setPassword(bCryptPasswordEncoder.encode(user.getPassword()));
        userService.add(user);
    }
}
