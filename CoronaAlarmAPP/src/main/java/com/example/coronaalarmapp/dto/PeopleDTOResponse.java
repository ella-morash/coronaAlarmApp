package com.example.coronaalarmapp.dto;

import java.util.List;

public class PeopleDTOResponse {
    private String firstName;
    private String lastName;
    private String email;
    private String dateOfBirth;
    private String phoneNumber;
    private Long guardianId;
    private PeopleDTOResponse guardian;
    private List<PeopleDTOResponse> children;
    private Long cityId;
    private Long areaId;
}
