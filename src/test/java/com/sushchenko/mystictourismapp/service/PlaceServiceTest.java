package com.sushchenko.mystictourismapp.service;

import com.sushchenko.mystictourismapp.entity.Place;
import com.sushchenko.mystictourismapp.entity.enums.Status;
import com.sushchenko.mystictourismapp.repo.PlaceRepo;
import org.junit.jupiter.api.DisplayNameGeneration;
import org.junit.jupiter.api.DisplayNameGenerator;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.*;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(SpringExtension.class)
@DisplayNameGeneration(DisplayNameGenerator.ReplaceUnderscores.class)
class PlaceServiceTest {
    @InjectMocks
    private PlaceService placeService;
    @Mock
    private PlaceRepo placeRepo;
    @Test
    void givePlace_whenAdd_thenPlaceAdded() {
        // given
        Place place = new Place();

//        // when
//        placeService.add(place);

        //then
        verify(placeRepo, times(1)).save(place);
        assertEquals(place.getStatus(), Status.UNCONFIRMED);
        assertEquals(place.getRating(), 0);
    }

    @Test
    void whenGetAll_thenReturnAllPlaces() {
        // given
        List<Place> places = new ArrayList<>();

        // when
        when(placeRepo.findAll()).thenReturn(places);
        placeService.getAll();

        // then
        verify(placeRepo, times(1)).findAll();
        verifyNoMoreInteractions(placeRepo);
    }

    @Test
    void giveListOfTags_whenGetAllByTags_thenReturnPlacesWithTags() {
//        // given
//        List<Place> places = new ArrayList<>();
//        when(placeRepo.findByTagsIn(anySet())).thenReturn(places);
//
//        // when
//        placeService.getAllByTags(anySet());
//
//        // then
//        verify(placeRepo, times(1)).findByTagsIn(Collections.emptySet());
    }

    @Test
    void givePlaceId_whenGetById_thenReturnPlace() {
        // given
        Place expected = new Place();
        when(placeRepo.findById(anyLong())).thenReturn(Optional.of(expected));

        // when
        Place actual = placeService.getById(anyLong());

        // then
        assertEquals(expected, actual);
        verify(placeRepo, times(1)).findById(anyLong());
        verifyNoMoreInteractions(placeRepo);
    }

    @Test
    void givePlaceIdAndRate_whenAddRatesById_thenPlaceRatingIsChanged() {
    }

    @Test
    void addPlaceAttachments() {
    }

    @Test
    void deletePlace() {
    }

    @Test
    void updatePlace() {
    }

}