package com.example.coronaalarmapp.controller;


import com.example.coronaalarmapp.dto.CityDTO;
import com.example.coronaalarmapp.service.CityService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
public class CityController {

    @Autowired
    private CityService cityService;

    @PostMapping(path = "/api/cities")
    @ResponseStatus(HttpStatus.CREATED)
    public void createCity(@Valid @RequestBody CityDTO cityDTO) {
        cityService.createCity(cityDTO);
    }

    @GetMapping(path = "/api/cities")
    public List<CityDTO> getAllCities() {
        return cityService.getAllCities();
    }

    @GetMapping(path = "/api/city")
    public CityDTO getCityByName(@RequestParam(name = "name",required = true) String name){
        return cityService.getCityByName(name);

    }

    @GetMapping(path = "/api/cities/{id}")
    public CityDTO getCityById(@PathVariable(name = "id") Long id) {
        return cityService.getCityById(id);
    }
}
