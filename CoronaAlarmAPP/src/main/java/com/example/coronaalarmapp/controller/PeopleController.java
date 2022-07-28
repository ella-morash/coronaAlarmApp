package com.example.coronaalarmapp.controller;


import com.example.coronaalarmapp.dto.MoveChildrenRequestDTO;
import com.example.coronaalarmapp.dto.MovePersonToCityRequestDTO;
import com.example.coronaalarmapp.dto.PeopleDTORequest;
import com.example.coronaalarmapp.dto.PeopleDTOResponse;
import com.example.coronaalarmapp.service.PeopleService;
import lombok.AllArgsConstructor;
import lombok.SneakyThrows;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
@AllArgsConstructor
public class PeopleController {


    private final PeopleService peopleService;

    @SneakyThrows
    @PostMapping(path = "/api/people")
    @ResponseStatus(HttpStatus.CREATED)
    public void createPerson(@Valid @RequestBody PeopleDTORequest peopleDTO){

        peopleService.createPerson(peopleDTO);
    }

    @PutMapping(path = "/api/people/{id}")
    public void updatePerson(@Valid @RequestBody PeopleDTORequest request,
                             @PathVariable(name = "id") Long id){
        peopleService.updatePerson(request,id);
    }

    @PostMapping(path = "/api/people/{id}/guardians")
    @ResponseStatus(HttpStatus.CREATED)
    public void addGuardianToPerson(@Valid @RequestBody PeopleDTORequest request,
                                    @PathVariable(name = "id")Long id) {
        peopleService.addGuardianToPerson(request,id);
    }

    @PatchMapping(path = "/api/people/{id}/guardians")
    @ResponseStatus(HttpStatus.CREATED)

    public void moveChildrenToAnotherGuardian(@Valid @RequestBody MoveChildrenRequestDTO request,
                                              @PathVariable(name = "id")Long id) {
        peopleService.moveChildrenToAnotherGuardian(request,id);
    }

    @GetMapping(path = "/api/people/{id}")
    public PeopleDTOResponse getPersonById(@PathVariable(name = "id") Long id) {
        return peopleService.getPersonById(id);
    }

    @GetMapping(path = "/api/people")
    public PeopleDTOResponse getPersonByEmail(@RequestParam(name = "email") String email) {
        return peopleService.getPersonByEmail(email);
    }

    @PostMapping(path = "/api/people/move")
    @ResponseStatus(HttpStatus.CREATED)
    public void movePersonToAnotherCity(@Valid @RequestBody MovePersonToCityRequestDTO request) {
        peopleService.movePersonToAnotherCity(request);
    }

}
