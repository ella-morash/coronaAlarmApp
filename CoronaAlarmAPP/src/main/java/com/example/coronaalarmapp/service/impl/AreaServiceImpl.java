package com.example.coronaalarmapp.service.impl;

import com.example.coronaalarmapp.dto.AreaDTO;
import com.example.coronaalarmapp.entity.Area;
import com.example.coronaalarmapp.entity.City;
import com.example.coronaalarmapp.repository.AreaRepository;
import com.example.coronaalarmapp.repository.CityRepository;
import com.example.coronaalarmapp.service.AreaService;
import com.example.coronaalarmapp.util.Convertor;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
@Transactional
@AllArgsConstructor
public class AreaServiceImpl implements AreaService {


    private final AreaRepository areaRepository;

    private final Convertor  convertor;

    private final CityRepository cityRepository;

    @Override
    public void createArea(AreaDTO areaDTO) {

        Area area = Area.builder()
                .name(areaDTO.getAreaName().toLowerCase())
                .areaCode(areaDTO.getAreaCode())
                .build();

        areaRepository.save(area);


    }

    @Override
    public List<Area> getAllAreas() {


        List<Area> areas = areaRepository.findAll();
        areas.forEach(area -> {
                    var citiesId = cityRepository.findAllByArea(area).stream()
                            .map(City::getId).toList();
                    convertor.convertFromAreaToDTO(area,citiesId);
                });

        return areas.isEmpty() ? new ArrayList<>(): areas;
    }

    @Override
    public AreaDTO getAreaByName(String name) {
        Optional<Area> areaOptional = Optional.ofNullable(areaRepository.getAreaByName(name.toLowerCase()));

        if (areaOptional.isEmpty()) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND,String.format("No area with name %s",name));
        }
        var citiesId = cityRepository.findAllByArea(areaOptional.get()).stream()
                .map(City::getId).toList();


        return convertor.convertFromAreaToDTO(areaOptional.get(),citiesId);


    }

    @Override
    public AreaDTO getAreaByID(Long id) {
        Area area = areaRepository.findById(id)
                .orElseThrow(()-> new ResponseStatusException(HttpStatus.NOT_FOUND,String.format("No area with id %d",id)));

        var citiesId = cityRepository.findAllByArea(area).stream()
                .map(City::getId).toList();
        return convertor.convertFromAreaToDTO(area,citiesId);
    }


}
