package com.example.coronaalarmapp.service.impl;

import com.example.coronaalarmapp.dto.PeopleDTORequest;
import com.example.coronaalarmapp.entity.Area;
import com.example.coronaalarmapp.entity.City;
import com.example.coronaalarmapp.entity.People;
import com.example.coronaalarmapp.repository.AreaRepository;
import com.example.coronaalarmapp.repository.CityRepository;
import com.example.coronaalarmapp.repository.PeopleRepository;
import com.example.coronaalarmapp.util.Convertor;
import com.example.coronaalarmapp.util.DateFormat;
import lombok.SneakyThrows;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;
import org.springframework.web.server.ResponseStatusException;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@ExtendWith(MockitoExtension.class)
public class PeopleServiceImplTest {

    @Mock
    private PeopleRepository peopleRepository;

    @Mock
    private CityRepository cityRepository;

    @Mock
    private AreaRepository areaRepository;

   @Mock
    private Convertor convertor;

    @Mock
    private DateFormat dateFormat;

    @InjectMocks
    private PeopleServiceImpl peopleService;


    @Test
    @DisplayName("should throw CONFLICT, where is a phone number duplicate")
    void shouldThrowConflictWherePhoneNumberDuplicate() {
        String phoneNumber = "109983";

        PeopleDTORequest request = PeopleDTORequest.builder()
                .phoneNumber(phoneNumber)
                .firstName("Alex")
                .build();

        Mockito
                .when(peopleRepository.existsByPhoneNumber(phoneNumber))
                .thenReturn(Boolean.TRUE);

        HttpStatus expectedStatus = HttpStatus.CONFLICT;
        String expectedMessage = String.format("there is already a phone number %s", phoneNumber);


        ResponseStatusException ex = Assertions.assertThrows(
                ResponseStatusException.class,
                () -> peopleService.createPerson(request)
        );


        Assertions.assertEquals(expectedStatus, ex.getStatus());
        Assertions.assertEquals(expectedMessage, ex.getReason());
    }


    @Test
    @DisplayName("should throw CONFLICT, where is an email duplicate")
    void shouldThrowConflictWhereEmailDuplicate() {


        PeopleDTORequest request = PeopleDTORequest.builder()
                .email("djgf@.com")
                .firstName("Alex")
                .guardianId(1L)
                .build();

        Mockito
                .when(peopleRepository.existsByEmail(request.getEmail()))
                .thenReturn(Boolean.TRUE);

        HttpStatus expectedStatus = HttpStatus.CONFLICT;
        String expectedMessage = String.format("there is already an email %s", request.getEmail());


        ResponseStatusException ex = Assertions.assertThrows(
                ResponseStatusException.class,
                () -> peopleService.createPerson(request)
        );


        Assertions.assertEquals(expectedStatus, ex.getStatus());
        Assertions.assertEquals(expectedMessage, ex.getReason());
    }

    @Test
    @DisplayName("should throw CONFLICT,when request has a guardian and children at the same time")
    void shouldThrowConflictWhenRequestHasGuardianAndChildren() {

        PeopleDTORequest request = PeopleDTORequest.builder()
                .email("djgf@.com")
                .firstName("Alex")
                .children(List.of(PeopleDTORequest.builder()
                                .firstName("John")
                                .build(),
                        PeopleDTORequest.builder()
                                .firstName("Julia")
                                .build()))
                .guardianId(1L)
                .build();

        Mockito
                .when(peopleRepository.existsByEmail(request.getEmail()))
                .thenReturn(Boolean.TRUE);

        HttpStatus expectedStatus = HttpStatus.CONFLICT;
        String expectedMessage = String.format("there is already an email %s", request.getEmail());


        ResponseStatusException ex = Assertions.assertThrows(
                ResponseStatusException.class,
                () -> peopleService.createPerson(request)
        );


        Assertions.assertEquals(expectedStatus, ex.getStatus());
        Assertions.assertEquals(expectedMessage, ex.getReason());
    }


    }





