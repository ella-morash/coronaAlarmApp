package com.example.coronaalarmapp.service.impl;

import com.example.coronaalarmapp.repository.PeopleRepository;
import com.example.coronaalarmapp.service.PeopleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class PeopleServiceImpl implements PeopleService {

    @Autowired
    private PeopleRepository peopleRepository;
}
