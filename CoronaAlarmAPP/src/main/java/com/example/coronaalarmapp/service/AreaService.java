package com.example.coronaalarmapp.service;

import com.example.coronaalarmapp.dto.AreaDTO;

import java.util.List;

public interface AreaService {
    void createArea(AreaDTO areaDTO);

    List<AreaDTO> getAllAreas();

    AreaDTO getAreaByName(String name);
}
