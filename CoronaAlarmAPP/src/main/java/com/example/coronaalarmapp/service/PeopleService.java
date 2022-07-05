package com.example.coronaalarmapp.service;


import com.example.coronaalarmapp.dto.MoveChildrenRequestDTO;
import com.example.coronaalarmapp.dto.PeopleDTORequest;
import com.example.coronaalarmapp.util.DateFormat;
import org.springframework.beans.factory.annotation.Autowired;

import java.text.ParseException;

public interface PeopleService {


    void createPerson(PeopleDTORequest peopleDTO) throws ParseException;

    void updatePerson(PeopleDTORequest request, Long id);

    void addGuardianToPerson(PeopleDTORequest request, Long id);

    void moveChildrenToAnotherGuardian(MoveChildrenRequestDTO request, Long id);
}
