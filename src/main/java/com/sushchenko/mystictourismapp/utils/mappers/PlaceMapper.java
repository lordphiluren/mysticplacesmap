package com.sushchenko.mystictourismapp.utils.mappers;

import com.sushchenko.mystictourismapp.entities.Place;
import com.sushchenko.mystictourismapp.services.UserService;
import com.sushchenko.mystictourismapp.web.dto.PlaceDTO;
import com.sushchenko.mystictourismapp.web.dto.UserDTO;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class PlaceMapper {
    private final ModelMapper modelMapper;
    private final UserService userService;

    public PlaceDTO mapToPlaceDTO(Place place) {
        UserDTO userDTO = modelMapper.map(userService.getById(place.getCreator()), UserDTO.class);
        PlaceDTO placeDTO  = modelMapper.map(place, PlaceDTO.class);
        placeDTO.setCreator(userDTO);
        return placeDTO;
    }
    public Place mapToPlace(PlaceDTO placeDTO) {
        return modelMapper.map(placeDTO, Place.class);
    }
}
