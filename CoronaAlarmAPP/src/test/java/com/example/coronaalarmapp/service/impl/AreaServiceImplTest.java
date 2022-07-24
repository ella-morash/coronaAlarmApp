package com.example.coronaalarmapp.service.impl;

import com.example.coronaalarmapp.dto.AreaDTO;
import com.example.coronaalarmapp.entity.Area;
import com.example.coronaalarmapp.repository.AreaRepository;
import com.example.coronaalarmapp.repository.CityRepository;
import com.example.coronaalarmapp.util.Convertor;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentMatchers;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;
import java.util.Optional;

@ExtendWith(MockitoExtension.class)
public class AreaServiceImplTest {

    @Mock
    private AreaRepository areaRepository;

    @Mock
    private CityRepository cityRepository;

    @Mock
    private  Convertor convertor;

    @InjectMocks
    private AreaServiceImpl areaService;




    @Test
    @DisplayName("should save area")
    void shouldSaveAreaWhenRequestIsNotNull() {

        AreaDTO areaDTO = AreaDTO.builder()
                .areaCode("WN")
                .areaName("Europa")
                .build();

        Area area = Area.builder()
                .name(areaDTO.getAreaName())
                .areaCode(areaDTO.getAreaCode())
                .id(1L)
                .build();

        Mockito
                .when(areaRepository.save(
                        ArgumentMatchers.any()
                ))
                .thenReturn(area);

        areaService.createArea(areaDTO);



    }

    @Test
    @DisplayName("should throw 404-NOT_FOUND, when there is no such area")
    void shouldThrowNotFoundWhenNoSuchAreaById() {

        AreaDTO areaDTO = AreaDTO.builder()
                .areaCode("WN")
                .areaName("Europa")
                .areaId(1L)
                .build();

        HttpStatus expectedStatus = HttpStatus.NOT_FOUND;
        String expectedMessage = String.format("No area with id %d", areaDTO.getAreaId());

        Mockito
                .when(areaRepository.findById(areaDTO.getAreaId()))
                        .thenReturn(Optional.empty());

        ResponseStatusException ex = Assertions.assertThrows(
                ResponseStatusException.class,
                () -> areaService.getAreaByID(areaDTO.getAreaId())
        );


        Assertions.assertEquals(expectedStatus, ex.getStatus());
        Assertions.assertEquals(expectedMessage, ex.getReason());



    }

    @Test
    @DisplayName("should return dto, when there is such area")
    void shouldReturnDTOWhenThereSuchAreaById() {


        Long requestedId = 1L;

        Area area = Area.builder()
                .areaCode("WN")
                .name("Europa")
                .id(requestedId)
                .build();

        AreaDTO areaDTO = AreaDTO.builder()
                .areaCode(area.getAreaCode())
                .areaName(area.getName())
                .areaId(area.getId())
                .build();


        Mockito
                .when(areaRepository.findById(requestedId))
                .thenReturn(Optional.of(area));

        Mockito
                .when(convertor.convertFromAreaToDTO(area,List.of()))
                .thenReturn(areaDTO);



        AreaDTO response = areaService.getAreaByID(area.getId());

        Assertions.assertEquals(area.getId(),response.getAreaId());
        Assertions.assertEquals(area.getName(),response.getAreaName());
        Assertions.assertEquals(area.getAreaCode(),response.getAreaCode());




    }

    @Test
    @DisplayName("should throw 404-NOT_FOUND, when there is no such area")
    void shouldThrowNotFoundWhenNoSuchAreaByName() {

        String name = "Europa";

        AreaDTO areaDTO = AreaDTO.builder()
                .areaCode("WN")
                .areaName(name.toLowerCase())
                .areaId(1L)
                .build();


        HttpStatus expectedStatus = HttpStatus.NOT_FOUND;
        String expectedMessage = String.format("No area with name %s", name);

        Mockito
                .when(areaRepository.getAreaByName(name.toLowerCase()))
                .thenReturn(null);



        ResponseStatusException ex = Assertions.assertThrows(
                ResponseStatusException.class,
                () -> areaService.getAreaByName(name)
        );


        Assertions.assertEquals(expectedStatus, ex.getStatus());
        Assertions.assertEquals(expectedMessage, ex.getReason());



    }

    @Test
    @DisplayName("should return dto, when there is such area")
    void shouldReturnDTOWhenThereSuchAreaByName() {

        String name = "Europa";



        Area area = Area.builder()
                .areaCode("WN")
                .name(name.toLowerCase())
                .id(1L)
                .build();

        AreaDTO areaDTO = AreaDTO.builder()
                .areaCode(area.getAreaCode())
                .areaName(area.getName())
                .areaId(area.getId())
                .build();


        Mockito
                .when(areaRepository.getAreaByName(name.toLowerCase()))
                .thenReturn(area);

        Mockito
                .when(convertor.convertFromAreaToDTO(area,List.of()))
                .thenReturn(areaDTO);

        AreaDTO response = areaService.getAreaByName(name);



        Assertions.assertEquals(area.getId(),response.getAreaId());
        Assertions.assertEquals(area.getName(),response.getAreaName());
        Assertions.assertEquals(area.getAreaCode(),response.getAreaCode());




    }

    @Test
    @DisplayName("should return empty list, when no areas found")
    void shouldReturnEmptyListWhenNoAreas() {

        Mockito
                .when(areaRepository.findAll())
                .thenReturn(List.of());

        List<Area> response = areaService.getAllAreas();

        Assertions.assertTrue(response.isEmpty());

    }

    @Test
    @DisplayName("should return list of areas")
    void shouldReturnEmptyListWhenThereAre() {

        int expectedListSize = 3;
        List<Area> areas = List.of(
                Area.builder()
                        .id(1L)
                        .name("europa")
                        .areaCode("wn")
                        .build(),
                Area.builder()
                        .id(2L)
                        .areaCode("hn")
                        .name("russia")
                        .build(),
                Area.builder()
                        .id(3L)
                        .areaCode("fb")
                        .name("usa")
                        .build()
        );

        Mockito
                .when(areaRepository.findAll())
                .thenReturn(areas);

        List<Area> response = areaService.getAllAreas();

        Assertions.assertFalse(response.isEmpty());

    }


}
