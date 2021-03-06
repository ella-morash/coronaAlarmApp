package com.example.coronaalarmapp.service.impl;


import com.example.coronaalarmapp.dto.MoveChildrenRequestDTO;
import com.example.coronaalarmapp.dto.MovePersonToCityRequestDTO;
import com.example.coronaalarmapp.dto.PeopleDTORequest;
import com.example.coronaalarmapp.dto.PeopleDTOResponse;
import com.example.coronaalarmapp.entity.Area;
import com.example.coronaalarmapp.entity.City;
import com.example.coronaalarmapp.entity.People;
import com.example.coronaalarmapp.repository.CityRepository;
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

import java.time.LocalDate;
import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;

@Service
public class PeopleServiceImpl implements PeopleService {

    @Autowired
    private PeopleRepository peopleRepository;

    @Autowired
    private CityRepository cityRepository;

    @Autowired
    private Convertor convertor;

    @Autowired
    private DateFormat dateFormat;

    @Override
    @SneakyThrows
    @Transactional
    public void createPerson(PeopleDTORequest request)  {


        if(request.getGuardianId() == 0) {
            peopleRepository.save(convertor.convertToPerson(request));
            return;
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
        if ( person.getGuardianId()==0||person.getGuardianId()==null) {
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

        } else {
            throw new ResponseStatusException(HttpStatus.CONFLICT,String.format("Person with id %d already has a guardian",person.getId()));

        }



    }

    @Override
    public void moveChildrenToAnotherGuardian(MoveChildrenRequestDTO request, Long id) {
        People fromGuardian = peopleRepository.findById(request.getFromGuardian())
                .orElseThrow(()->new ResponseStatusException(HttpStatus.NOT_FOUND,
                        String.format("No such guardian with id %d",request.getFromGuardian())));

        //verify, all children belong to a specific `fromGuardian`, or else - 422
        //Error should indicate: which of the children does not belong to a specific from guardian

        List<People> children = peopleRepository.findPeopleByGuardianId(fromGuardian.getId());
        Set<People> set = new HashSet<>(children);
        List<Optional<People>> requestChildren = request.getChildrenIds().stream().map(i->peopleRepository.findById(i)).toList();

        requestChildren.stream().filter(ch -> ch.isPresent() && set.add(ch.get())).forEach(ch -> {
            throw new ResponseStatusException(HttpStatus.UNPROCESSABLE_ENTITY,
                    String.format("Person with id %d does not belong to this guardian", ch.get().getId()));
        });

        //- verify, that `toGuardian` is not a child and does not have a guardian
        People toGuardian = peopleRepository.findById(request.getToGuardian())
                .orElseThrow(()->new ResponseStatusException(HttpStatus.NOT_FOUND,
                        String.format("No such guardian with id %d",request.getToGuardian())));



        //- on move, childrens??? city and area should be changed to guardians city and area
       if (toGuardian.getGuardianId() == 0 || toGuardian.getGuardianId() == null) {
           children.forEach(person-> {
               People.builder()
                       .guardianId(toGuardian.getId())
                       .area(toGuardian.getArea())
                       .city(toGuardian.getCity())
                       .build();
               peopleRepository.save(person);

           });

       } else {
           throw new ResponseStatusException(HttpStatus.CONFLICT,String.format("Person with id %d can not be a guardian",request.getToGuardian()));

       }

    }

    @Override
    public PeopleDTOResponse getPersonById(Long id) {
        return convertor.convertPersonToPeopleDTOResponse(peopleRepository.findById(id)
                .orElseThrow(()->new ResponseStatusException(HttpStatus.NOT_FOUND,
                        String.format("No such person with id %d",id))));
    }

    @Override
    public PeopleDTOResponse getPersonByEmail(String email) { // should I throw any exception here ?
        return convertor.convertPersonToPeopleDTOResponse(peopleRepository.findByEmail(email));
    }

    @Override
    public void movePersonToAnotherCity(MovePersonToCityRequestDTO request) {

        City fromCity = cityRepository.findById(request.getFromCityId())
                .orElseThrow(()->new ResponseStatusException(HttpStatus.NOT_FOUND,
                        String.format("No such city with id %d",request.getFromCityId())));

        People person = peopleRepository.findById(request.getPersonId())
                .orElseThrow(()->new ResponseStatusException(HttpStatus.NOT_FOUND,
                        String.format("No such person with id %d",request.getPersonId())));

        if (!person.getCity().getId().equals(fromCity.getId())) {
            throw new ResponseStatusException(HttpStatus.CONFLICT,String.format("Person is located in another city with id %d",person.getCity().getId()));
        }

        City toCity = cityRepository.findById(request.getToCityId())
                .orElseThrow(()->new ResponseStatusException(HttpStatus.NOT_FOUND,
                        String.format("No such city with id %d",request.getToCityId())));



        if (person.getGuardianId() == 0 || person.getGuardianId() == null) {
            //- on moving parent - all children get moved as well
            person.setCity(toCity);
            peopleRepository.save(person);
            List<People> children = peopleRepository.findPeopleByGuardianId(person.getId());

            if (!children.isEmpty()) {
                children.forEach(ch ->
                        People.builder()
                                .city(toCity)
                                .build()
                );
            }
            peopleRepository.saveAll(children);



        } else { // - person with a guardian cannot be moved - 422 UNPROCESSABLE_ENTITY + reason
            throw new ResponseStatusException(HttpStatus.UNPROCESSABLE_ENTITY,"Person has a guardian and can not be moved");
        }




    }


}


