package com.sushchenko.mystictourismapp.utils.mappers;

import com.sushchenko.mystictourismapp.entities.Place;
import com.sushchenko.mystictourismapp.services.UserService;
import com.sushchenko.mystictourismapp.web.dto.CommentDTO;
import com.sushchenko.mystictourismapp.web.dto.PlaceDTO;
import com.sushchenko.mystictourismapp.web.dto.UserDTO;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@RequiredArgsConstructor
public class PlaceMapper {
    private final ModelMapper modelMapper;
    private final UserService userService;

    public PlaceDTO mapToPlaceDTO(Place place) {
        UserDTO userDTO = modelMapper.map(place.getCreator(), UserDTO.class);
        List<CommentDTO> commentsDto = place.getComments()
                .stream()
                .map(c -> modelMapper.map(c, CommentDTO.class))
                .toList();
        PlaceDTO placeDTO  = modelMapper.map(place, PlaceDTO.class);
        placeDTO.setCreator(userDTO);
        placeDTO.setComments(commentsDto);
        return placeDTO;
    }
    public Place mapToPlace(PlaceDTO placeDTO) {
        return modelMapper.map(placeDTO, Place.class);
    }
}
