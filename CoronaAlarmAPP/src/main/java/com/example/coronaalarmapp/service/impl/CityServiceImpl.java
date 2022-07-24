package com.example.coronaalarmapp.service.impl;

import com.example.coronaalarmapp.dto.CityDTO;
import com.example.coronaalarmapp.entity.Area;
import com.example.coronaalarmapp.entity.City;
import com.example.coronaalarmapp.entity.severitystatus.SeverityStatus;
import com.example.coronaalarmapp.repository.AreaRepository;
import com.example.coronaalarmapp.repository.CityRepository;
import com.example.coronaalarmapp.repository.NotificationRepository;
import com.example.coronaalarmapp.service.CityService;
import com.example.coronaalarmapp.util.Convertor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;
import java.util.Optional;

@Service
public class CityServiceImpl implements CityService {

    @Autowired
    private CityRepository cityRepository;

    @Autowired
    private AreaRepository areaRepository;

    @Autowired
    private Convertor convertor;

    @Autowired
    private NotificationRepository notificationRepository;

    @Override
    public void createCity(CityDTO cityDTO) {
        Area area = areaRepository.findById(cityDTO.getAreaId())
                .orElseThrow(()->
                        new ResponseStatusException(HttpStatus.NOT_FOUND,
                                String.format("No area with id %d",cityDTO.getAreaId())));
        SeverityStatus status = notificationRepository.findByArea_Id(area.getId());

        cityRepository.save(convertor.convertFromCityDTOToCity(cityDTO,area,status));

    }

    @Override
    public List<CityDTO> getAllCities() {
        List<CityDTO> cities = cityRepository.findAll()
                .stream()
                .map(city->convertor.convertFromCityToDTO(city)).toList();

        if (cities.isEmpty()) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "No cities found");
        }

        return cities;
    }

    @Override
    public CityDTO getCityByName(String name) {
        City city = cityRepository.findByName(name.toLowerCase());
        if (city == null) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND,String.format("No area with name %s",name));
        }

        return convertor.convertFromCityToDTO(city);


    }

    @Override
    public CityDTO getCityById(Long id) {
        var city = cityRepository.findById(id)
                .orElseThrow(()->new ResponseStatusException(HttpStatus.NOT_FOUND,String.format("No city with id %d",id)));
        return convertor.convertFromCityToDTO(city);
    }


}
