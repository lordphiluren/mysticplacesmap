package com.sushchenko.mystictourismapp.security;


import com.auth0.jwt.interfaces.DecodedJWT;
import com.sushchenko.mystictourismapp.entities.User;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;

import java.util.Collections;
import java.util.List;

@Component
@RequiredArgsConstructor
public class JwtToPrincipalConverter {
    private final CustomUserDetailService userDetailService;
    public UserDetails convert(DecodedJWT jwt) {
        return userDetailService.loadUserByUsername(jwt.getClaim("username").asString());
//        return UserPrincipal.builder()
//                .userId(jwt.getSubject())
//                .username(jwt.getClaim("username").asString())
//                .authorities(extractAuthoritiesFromClaim(jwt))
//                .build();
    }

    private List<SimpleGrantedAuthority> extractAuthoritiesFromClaim(DecodedJWT jwt) {
        var claim = jwt.getClaim("authorities");
        if (claim.isNull() || claim.isMissing()) return List.of();
        return claim.asList(SimpleGrantedAuthority.class);
    }
}
