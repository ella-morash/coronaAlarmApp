package com.example.coronaalarmapp.service.impl;


import com.example.coronaalarmapp.dto.PeopleDTORequest;
import com.example.coronaalarmapp.entity.People;
import com.example.coronaalarmapp.repository.PeopleRepository;
import com.example.coronaalarmapp.service.PeopleService;
import com.example.coronaalarmapp.util.Convertor;
import com.example.coronaalarmapp.util.DateFormat;
import lombok.SneakyThrows;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;

import javax.persistence.Table;
import java.text.ParseException;
import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@Service
public class PeopleServiceImpl implements PeopleService {

    @Autowired
    private PeopleRepository peopleRepository;

    @Autowired
    private Convertor convertor;

    @Autowired
    private DateFormat dateFormat;

    @Override
    @SneakyThrows
    @Transactional
    public void createPerson(PeopleDTORequest request)  {


        if(request.getGuardianId() == null) {
            peopleRepository.save(convertor.convertToPerson(request));
        }

        // prevent a guardian of a guardian
        Optional<People> person = Optional.ofNullable(peopleRepository.findByEmail(request.getEmail()));
        if (person.isPresent()) {
            long id = person.get().getId();
            List<People> potentialChildren = peopleRepository.findPeopleByGuardianId(id);
            if (!potentialChildren.isEmpty()) {
                throw new ResponseStatusException(HttpStatus.CONFLICT,"This person a guardian itself");
            }
        }

        People guardianToBe = peopleRepository.findById(request.getGuardianId())
                .orElseThrow(()->new ResponseStatusException(HttpStatus.NOT_FOUND,String.format("no person with id %d",request.getGuardianId())));
        // a guardian should be 18+ years old from now
        LocalDate now = LocalDate.now();
        long diff = dateFormat.difference(guardianToBe.getDateOfBirth(),now);
        if (diff < 18) {
            throw new ResponseStatusException(HttpStatus.CONFLICT, "The age should be greater then 18 ");

        }
        peopleRepository.save(convertor.convertToPerson(request));


    }

    @SneakyThrows
    @Override
    public void updatePerson(PeopleDTORequest request, Long id) {
        People person = peopleRepository.findById(id)
                .orElseThrow(()->new ResponseStatusException(HttpStatus.NOT_FOUND,String.format("No person with id %d",id)));

        person.setFirstName(request.getFirstName());
        person.setLastName(request.getLastName());
        person.setEmail(request.getEmail());
        person.setPhoneNumber(request.getPhoneNumber());

        peopleRepository.save(person);



    }

    @SneakyThrows
    @Override
    @Transactional
    public void addGuardianToPerson(PeopleDTORequest request, Long id) {
        People person = peopleRepository.findById(id)
                .orElseThrow(()->new ResponseStatusException(HttpStatus.NOT_FOUND,String.format("No person with id %d",id)));

        LocalDate now = LocalDate.now();
        long diff = dateFormat.difference(request.getDateOfBirth(),now);
        if (diff < 18) {
            throw new ResponseStatusException(HttpStatus.CONFLICT, "The age should be greater then 18 ");

        }

        People guardianToAdd = People.builder()
                .firstName(request.getFirstName())
                .lastName(request.getLastName())
                .email(request.getEmail())
                .dateOfBirth(request.getDateOfBirth())
                .phoneNumber(request.getPhoneNumber())
                .build();
        peopleRepository.save(guardianToAdd);

        person.setGuardianId(guardianToAdd.getId());

        peopleRepository.save(person);

    }
}


