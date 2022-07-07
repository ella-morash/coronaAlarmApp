package com.example.coronaalarmapp.controller;


import com.example.coronaalarmapp.dto.AreaDTO;
import com.example.coronaalarmapp.service.AreaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class AreaController {

    @Autowired
    private AreaService areaService;

    @PostMapping(path = "/api/areas")
    @ResponseStatus(HttpStatus.CREATED)
    public void createArea(@RequestBody AreaDTO areaDTO) {

        areaService.createArea(areaDTO);
    }

    @GetMapping(path = "/api/areas")
    public List<AreaDTO> getAllAreas() {

        return areaService.getAllAreas();
    }



    @GetMapping(path = "/api/areas")
    public AreaDTO getAreaByName(@RequestParam(name = "name") String name) {
        return areaService.getAreaByName(name);
    }
}
