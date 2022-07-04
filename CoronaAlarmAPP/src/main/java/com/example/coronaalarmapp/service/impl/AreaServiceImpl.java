package com.example.coronaalarmapp.service.impl;

import com.example.coronaalarmapp.dto.AreaDTO;
import com.example.coronaalarmapp.entity.Area;
import com.example.coronaalarmapp.repository.AreaRepository;
import com.example.coronaalarmapp.service.AreaService;
import com.example.coronaalarmapp.util.Convertor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;
import java.util.Optional;

@Service
@Transactional
public class AreaServiceImpl implements AreaService {

    @Autowired
    private AreaRepository areaRepository;
    @Autowired
    private Convertor  convertor;

    @Override
    public void createArea(AreaDTO areaDTO) {
        areaRepository.save(convertor.convertFromDTOToArea(areaDTO));


    }

    @Override
    public List<AreaDTO> getAllAreas() {
        List<AreaDTO> areaDTOS = areaRepository.findAll()
                .stream()
                .map(area -> convertor.convertFromAreaToDTO(area)).toList();
        if (areaDTOS.isEmpty()) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND,"No areas found");
        }
        return areaDTOS;
    }

    @Override
    public AreaDTO getAreaByName(String name) {
        Optional<Area> areaOptional = Optional.ofNullable(areaRepository.getAreaByName(name.toLowerCase()));
        if (areaOptional.isEmpty()) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND,String.format("No area with name %s",name));
        }

        return convertor.convertFromAreaToDTO(areaOptional.get());


    }


}
