package com.example.coronaalarmapp.controller;


import com.example.coronaalarmapp.dto.AreaDTO;
import com.example.coronaalarmapp.entity.Area;
import com.example.coronaalarmapp.service.AreaService;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@AllArgsConstructor
public class AreaController {


    private final AreaService areaService;

    @PostMapping(path = "/api/areas")
    @ResponseStatus(HttpStatus.CREATED)
    public void createArea(@Valid @RequestBody AreaDTO areaDTO) {

        areaService.createArea(areaDTO);
    }

    @GetMapping(path = "/api/areas/all")
    public List<Area> getAllAreas() {

        return areaService.getAllAreas();
    }



    @GetMapping(path = "/api/areas")
    public AreaDTO getAreaByName(@RequestParam(name = "name") String name) {

        return areaService.getAreaByName(name);
    }

    @GetMapping(path = "/api/areas/area")
    public AreaDTO getAreaById(@RequestParam(name = "id") Long id) {
        return areaService.getAreaByID(id);
    }
}
