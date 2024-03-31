package com.sushchenko.mystictourismapp.service.helper;

import com.sushchenko.mystictourismapp.entity.User;

import java.util.Objects;

public class Helper {
    public static boolean checkUserPermissions(User creator, User userPrincipal) {
        return Objects.equals(creator.getId(), userPrincipal.getId());
    }
}
