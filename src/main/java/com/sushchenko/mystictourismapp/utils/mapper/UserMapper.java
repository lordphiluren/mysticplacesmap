package com.sushchenko.mystictourismapp.utils.mapper;

import com.sushchenko.mystictourismapp.entity.User;
import com.sushchenko.mystictourismapp.web.dto.UserRequest;
import com.sushchenko.mystictourismapp.web.dto.UserResponse;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class UserMapper {
    private final ModelMapper modelMapper;
    public User toEntity(UserRequest userDto) {
        return modelMapper.map(userDto, User.class);
    }

    public UserResponse toDto(User user) {
        return modelMapper.map(user, UserResponse.class);
    }
//    public void mergeDtoIntoEntity()

}
