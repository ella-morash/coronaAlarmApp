package com.example.coronaalarmapp.service;

import com.example.coronaalarmapp.dto.CityDTO;

import java.util.List;

public interface CityService {
    void createCity(CityDTO cityDTO);

    List<CityDTO> getAllCities();

    CityDTO getCityByName(String name);

    CityDTO getCityById(Long id);
}
