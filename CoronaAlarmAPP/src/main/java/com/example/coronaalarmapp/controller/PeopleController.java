package com.example.coronaalarmapp.controller;


import com.example.coronaalarmapp.service.PeopleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class PeopleController {

    @Autowired
    private PeopleService peopleService;
}
