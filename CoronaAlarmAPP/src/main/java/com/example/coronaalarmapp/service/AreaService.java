package com.example.coronaalarmapp.service;

import com.example.coronaalarmapp.dto.AreaDTO;
import com.example.coronaalarmapp.entity.Area;

import java.util.List;

public interface AreaService {
    void createArea(AreaDTO areaDTO);

    List<Area> getAllAreas();

    AreaDTO getAreaByName(String name);

    AreaDTO getAreaByID(Long id);
}
