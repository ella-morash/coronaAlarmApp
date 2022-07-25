package com.example.coronaalarmapp.service.impl;


import com.example.coronaalarmapp.dto.AreaDTO;
import com.example.coronaalarmapp.dto.CityDTO;
import com.example.coronaalarmapp.entity.Area;
import com.example.coronaalarmapp.entity.City;
import com.example.coronaalarmapp.entity.severitystatus.SeverityStatus;
import com.example.coronaalarmapp.repository.AreaRepository;
import com.example.coronaalarmapp.repository.CityRepository;
import com.example.coronaalarmapp.repository.NotificationRepository;
import com.example.coronaalarmapp.util.Convertor;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;
import java.util.Optional;

@ExtendWith(MockitoExtension.class)
public class CityServiceImplTest {

    @Mock
    private CityRepository cityRepository;

    @Mock
    private AreaRepository areaRepository;

    @Mock
    private AreaServiceImpl areaService;

    @Mock
    private Convertor convertor;

    @Mock
    private NotificationRepository notificationRepository;

    @InjectMocks
    private CityServiceImpl cityService;


    @Test
    @DisplayName("should throw 404-NOT FOUND,where there is no area")
    void shouldThrowNotFoundWhereNoArea() {
        CityDTO cityDTO = CityDTO.builder()
                .areaId(1L)
                .cityName("berlin")
                .status(SeverityStatus.GREEN)
                .build();
        Long areaId = cityDTO.getAreaId();


        HttpStatus expectedStatus = HttpStatus.NOT_FOUND;
        String expectedMessage = String.format("No area with id %d", areaId);

        Mockito
                .when(areaRepository.findById(areaId))
                .thenReturn(Optional.empty());

        ResponseStatusException ex = Assertions.assertThrows(
                ResponseStatusException.class,
                () -> cityService.createCity(cityDTO)
        );


        Assertions.assertEquals(expectedStatus, ex.getStatus());
        Assertions.assertEquals(expectedMessage, ex.getReason());
    }

    @Test
    @DisplayName("should throw HttpStatus.CONFLICT, when there is a duplicate city name")
    void shouldThrowConflictWhereCityNameDuplicate() {

        CityDTO cityDTO = CityDTO.builder()
                .areaId(1L)
                .cityName("berlin")
                .status(SeverityStatus.GREEN)
                .build();


        HttpStatus expectedStatus = HttpStatus.CONFLICT;
        String expectedMessage = String.format("City with name %s already exists", cityDTO.getCityName());

        Mockito
                .when(areaRepository.findById(cityDTO.getAreaId()))
                .thenReturn(Optional.of(Area.builder()
                                .id(1L)
                                .name("europa")
                                .build()));

        Mockito
                .when(cityRepository.existsByName(cityDTO.getCityName()))
                .thenReturn(Boolean.TRUE);

        ResponseStatusException ex = Assertions.assertThrows(
                ResponseStatusException.class,
                () -> cityService.createCity(cityDTO)
        );


        Assertions.assertEquals(expectedStatus, ex.getStatus());
        Assertions.assertEquals(expectedMessage, ex.getReason());


    }

    @Test
    @DisplayName("should return empty list, when no cities found")
    void shouldReturnEmptyListWhenNoCities() {

        Mockito
                .when(cityRepository.findAll())
                .thenReturn(List.of());

        var response = cityService.getAllCities();

        Assertions.assertTrue(response.isEmpty());
    }


    @Test
    @DisplayName("should return list of cities, when there are cities")
    void shouldReturnCitiesWhenThereAre() {

        int expectedListSize = 3;
        List<City> areas = List.of(
                City.builder()
                        .id(1L)
                        .name("europa")
                        .build(),
                City.builder()
                        .id(2L)
                        .name("russia")
                        .build(),
                City.builder()
                        .id(3L)
                        .name("usa")
                        .build()
        );

        Mockito
                .when(cityRepository.findAll())
                .thenReturn(areas);

        var response = cityService.getAllCities();

        Assertions.assertFalse(response.isEmpty());
        Assertions.assertEquals(expectedListSize,areas.size());
    }

    @Test
    @DisplayName("should return NOT_FOUND,when there is no city with given name")
    void shouldThrowNotFoundWhereThereNoCityByName() {

        String name = "berlin";



        HttpStatus expectedStatus = HttpStatus.NOT_FOUND;
        String expectedMessage = String.format("No city with name %s", name);

        Mockito
                .when(cityRepository.findByName(name))
                .thenReturn(null);



        ResponseStatusException ex = Assertions.assertThrows(
                ResponseStatusException.class,
                () -> cityService.getCityByName(name)
        );


        Assertions.assertEquals(expectedStatus, ex.getStatus());
        Assertions.assertEquals(expectedMessage, ex.getReason());


    }

    @Test
    @DisplayName("should return city dto, when there is such city with given name")
    void shouldReturnCityDtoWhenThereCityByName() {

        String name = "berlin";

        City city = City.builder()
                .id(1L)
                .name(name)
                .status(SeverityStatus.GREEN)
                .build();

        CityDTO cityDTO = CityDTO.builder()
                .cityId(city.getId())
                .status(city.getStatus())
                .cityName(city.getName())
                .build();


        Mockito
                .when(cityRepository.findByName(name))
                .thenReturn(city);

        Mockito
                .when(convertor.convertFromCityToDTO(city))
                .thenReturn(cityDTO);

        var response = cityService.getCityByName(name);



        Assertions.assertEquals(city.getId(),response.getCityId());
        Assertions.assertEquals(city.getName(),response.getCityName());
        Assertions.assertEquals(city.getStatus(),response.getStatus());

    }

    @Test
    @DisplayName("should throw NOT_FOUND, where there is no city with given id")
    void shouldThrowNotFoundWhereNoCityById() {

        Long id = 1L;

        HttpStatus expectedStatus = HttpStatus.NOT_FOUND;
        String expectedMessage = String.format("No city with id %d", id);

        Mockito
                .when(cityRepository.findById(id))
                .thenReturn(Optional.empty());



        ResponseStatusException ex = Assertions.assertThrows(
                ResponseStatusException.class,
                () -> cityService.getCityById(id)
        );


        Assertions.assertEquals(expectedStatus, ex.getStatus());
        Assertions.assertEquals(expectedMessage, ex.getReason());

    }

    @Test
    @DisplayName("should return city dto, when there is such city with given id")
    void shouldReturnCityDtoWhenThereCityById() {

        Long requestedId = 1L;

       City city = City.builder()
               .id(requestedId)
               .name("berlin")
               .build();

        CityDTO cityDTO = CityDTO.builder()
                .cityName(city.getName())
                .cityId(city.getId())
                .build();


        Mockito
                .when(cityRepository.findById(requestedId))
                .thenReturn(Optional.of(city));

        Mockito
                .when(convertor.convertFromCityToDTO(city))
                .thenReturn(cityDTO);



        var response = cityService.getCityById(requestedId);

        Assertions.assertEquals(city.getId(),response.getCityId());
        Assertions.assertEquals(city.getName(),response.getCityName());


    }

}
