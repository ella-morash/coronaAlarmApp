package com.example.coronaalarmapp.util;

import com.example.coronaalarmapp.dto.AreaDTO;
import com.example.coronaalarmapp.dto.CityDTO;
import com.example.coronaalarmapp.entity.Area;
import com.example.coronaalarmapp.entity.City;
import com.example.coronaalarmapp.repository.CityRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Locale;

@Component
public class Convertor {

    @Autowired
    private CityRepository cityRepository;

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

}
