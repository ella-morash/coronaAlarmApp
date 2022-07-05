package com.example.coronaalarmapp.util;

import com.example.coronaalarmapp.dto.AreaDTO;
import com.example.coronaalarmapp.dto.CityDTO;
import com.example.coronaalarmapp.dto.PeopleDTORequest;
import com.example.coronaalarmapp.entity.Area;
import com.example.coronaalarmapp.entity.City;
import com.example.coronaalarmapp.entity.People;
import com.example.coronaalarmapp.repository.AreaRepository;
import com.example.coronaalarmapp.repository.CityRepository;
import com.example.coronaalarmapp.repository.PeopleRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;


@Component
public class Convertor {

    @Autowired
    private CityRepository cityRepository;

    @Autowired
    private PeopleRepository peopleRepository;

    @Autowired
    private AreaRepository areaRepository;

    public Area convertFromDTOToArea(AreaDTO areaDTO) {
        return Area.builder()
                .name(areaDTO.getAreaName().toLowerCase())
                .build();
    }

    public  AreaDTO convertFromAreaToDTO(Area area) {
        List<Long> citiesId = cityRepository.findAllByArea(area).stream()
                .map(City::getId).toList();
        return AreaDTO.builder().areaId(area.getId())
                .areaName(area.getName())
                .citiesId(citiesId)
                .build();
    }

    public City convertFromCityDTOToCity(CityDTO cityDTO,Area area) {
        return City.builder()
                .name(cityDTO.getCityName().toLowerCase())
                .area(area)
                .build();
    }

    public CityDTO convertFromCityToDTO(City city) {
        return CityDTO.builder()
                .cityId(city.getId())
                .cityName(city.getName())
                .areaId(city.getArea().getId())
                .build();

    }

    public People convertToPerson(PeopleDTORequest request) {
        City city = cityRepository.findById(request.getCityId()).
                orElseThrow(()->new ResponseStatusException(HttpStatus.NOT_FOUND,String.format("No city with id %d",request.getCityId())));
        Area area = areaRepository.findById(request.getAreaId()).
                orElseThrow(()->new ResponseStatusException(HttpStatus.NOT_FOUND,String.format("No area with id %d",request.getAreaId())));

        return People.builder()
                .firstName(request.getFirstName())
                .lastName(request.getLastName())
                .email(request.getEmail())
                .dateOfBirth(request.getDateOfBirth())
                .phoneNumber(request.getPhoneNumber())
                .guardianId(request.getGuardianId())
                .area(area)
                .city(city)
                .build();
    }



}

