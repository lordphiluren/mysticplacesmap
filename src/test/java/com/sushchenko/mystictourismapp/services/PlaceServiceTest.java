package com.sushchenko.mystictourismapp.services;

import com.sushchenko.mystictourismapp.entities.Place;
import com.sushchenko.mystictourismapp.entities.enums.Status;
import com.sushchenko.mystictourismapp.repos.PlaceRepo;
import com.sushchenko.mystictourismapp.utils.filemanager.PlaceFileManager;
import jakarta.inject.Inject;
import org.junit.jupiter.api.DisplayNameGeneration;
import org.junit.jupiter.api.DisplayNameGenerator;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.security.core.parameters.P;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.awt.*;
import java.util.*;
import java.util.List;
import java.util.stream.Collectors;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(SpringExtension.class)
@DisplayNameGeneration(DisplayNameGenerator.ReplaceUnderscores.class)
class PlaceServiceTest {
    @InjectMocks
    private PlaceService placeService;
    @Mock
    private PlaceRepo placeRepo;
    @Mock
    private PlaceFileManager fileManager;
    @Test
    void givePlace_whenAdd_thenPlaceAdded() {
        // given
        Place place = new Place();

        // when
        placeService.add(place);

        //then
        verify(placeRepo, times(1)).save(place);
        assertEquals(place.getStatus(), Status.UNCONFIRMED);
        assertIterableEquals(place.getRates(), Collections.emptyList());
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
        // given
        List<Place> places = new ArrayList<>();
        when(placeRepo.findByTagsIn(anyList())).thenReturn(places);

        // when
        placeService.getAllByTags(anyList());

        // then
        verify(placeRepo, times(1)).findByTagsIn(Collections.emptyList());
    }

    @Test
    void givePlaceId_whenGetById_thenReturnPlace() {
        // given
        Place expected = new Place();
        when(placeRepo.findById(anyString())).thenReturn(Optional.of(expected));

        // when
        Place actual = placeService.getById(anyString());

        // then
        assertEquals(expected, actual);
        verify(placeRepo, times(1)).findById(anyString());
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

    private double countPlaceRating(Place place) {
        List<Double> rates = place.getRates();
        return rates.stream().mapToDouble(Double::doubleValue).sum() / rates.size();
    }
}