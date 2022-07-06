package com.example.coronaalarmapp.controller;


import com.example.coronaalarmapp.dto.MoveChildrenRequestDTO;
import com.example.coronaalarmapp.dto.MovePersonToCityRequestDTO;
import com.example.coronaalarmapp.dto.PeopleDTORequest;
import com.example.coronaalarmapp.dto.PeopleDTOResponse;
import com.example.coronaalarmapp.service.PeopleService;
import lombok.SneakyThrows;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
public class PeopleController {

    @Autowired
    private PeopleService peopleService;

    @SneakyThrows
    @PostMapping(path = "/api/people") // NOT FOUND
    public void createPerson(@RequestBody PeopleDTORequest peopleDTO){

        peopleService.createPerson(peopleDTO);
    }

    @PutMapping(path = "/api/people/{id}")
    public void updatePerson(@RequestBody PeopleDTORequest request,
                             @PathVariable(name = "id") Long id){
        peopleService.updatePerson(request,id);
    }

    @PostMapping(path = "/api/people/{id}/guardians")
    public void addGuardianToPerson(@RequestBody PeopleDTORequest request,
                                    @PathVariable(name = "id")Long id) {
        peopleService.addGuardianToPerson(request,id);
    }

    @PatchMapping(path = "/api/people/{id}/guardians")
    public void moveChildrenToAnotherGuardian(@RequestBody MoveChildrenRequestDTO request,
                                              @PathVariable(name = "id")Long id) {
        peopleService.moveChildrenToAnotherGuardian(request,id);
    }

    @GetMapping(path = "/api/people/{id}")
    public PeopleDTOResponse getPersonById(@PathVariable(name = "id") Long id) {
        return peopleService.getPersonById(id);
    }

    @GetMapping(path = "/api/people?email={email}")
    public PeopleDTOResponse getPersonByEmail(@RequestParam(name = "email") String email) {
        return peopleService.getPersonByEmail(email);
    }

    @PostMapping(path = "/api/people/move")
    public void movePersonToAnotherCity(MovePersonToCityRequestDTO request) {
        peopleService.movePersonToAnotherCity(request);
    }

}
