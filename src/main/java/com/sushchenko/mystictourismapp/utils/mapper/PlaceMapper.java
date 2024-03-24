package com.sushchenko.mystictourismapp.utils.mapper;

import com.sushchenko.mystictourismapp.entity.Place;
import com.sushchenko.mystictourismapp.entity.PlaceTag;
import com.sushchenko.mystictourismapp.web.dto.CommentResponse;
import com.sushchenko.mystictourismapp.web.dto.PlaceRequest;
import com.sushchenko.mystictourismapp.web.dto.PlaceResponse;
import com.sushchenko.mystictourismapp.web.dto.UserResponse;
import lombok.RequiredArgsConstructor;
import org.modelmapper.Conditions;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;

import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Component
@RequiredArgsConstructor
public class PlaceMapper {
    private final ModelMapper modelMapper;

    public PlaceResponse toDto(Place place) {
        UserResponse userDto = modelMapper.map(place.getCreator(), UserResponse.class);
        Set<String> tagsDto = place.getTags().stream()
                .map(tag -> tag.getId().getTag())
                .collect(Collectors.toSet());
        PlaceResponse placeDto  = modelMapper.map(place, PlaceResponse.class);
        placeDto.setCreator(userDto);
        placeDto.setTags(tagsDto);
        return placeDto;
    }
    public Place toEntity(PlaceRequest placeDto) {
        return modelMapper.map(placeDto, Place.class);
    }
    public void mergeDtoIntoEntity(PlaceRequest placeDto, Place place) {
        modelMapper.getConfiguration().setPropertyCondition(Conditions.isNotNull());
        modelMapper.map(placeDto, place);
    }
}
