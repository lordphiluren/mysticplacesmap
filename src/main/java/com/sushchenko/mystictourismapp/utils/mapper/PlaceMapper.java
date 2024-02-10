package com.sushchenko.mystictourismapp.utils.mapper;

import com.sushchenko.mystictourismapp.entity.Place;
import com.sushchenko.mystictourismapp.web.dto.CommentResponse;
import com.sushchenko.mystictourismapp.web.dto.PlaceRequest;
import com.sushchenko.mystictourismapp.web.dto.PlaceResponse;
import com.sushchenko.mystictourismapp.web.dto.UserResponse;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@RequiredArgsConstructor
public class PlaceMapper {
    private final ModelMapper modelMapper;

    public PlaceResponse toDto(Place place) {
        UserResponse userDto = modelMapper.map(place.getCreator(), UserResponse.class);
        List<CommentResponse> commentsDto = place.getComments()
                .stream()
                .map(c -> modelMapper.map(c, CommentResponse.class))
                .toList();
        PlaceResponse placeDto  = modelMapper.map(place, PlaceResponse.class);
        placeDto.setCreator(userDto);
        placeDto.setComments(commentsDto);
        return placeDto;
    }
    public Place toEntity(PlaceRequest placeDto) {
        return modelMapper.map(placeDto, Place.class);
    }
}