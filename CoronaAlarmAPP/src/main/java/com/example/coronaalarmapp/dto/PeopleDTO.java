package com.example.coronaalarmapp.dto;


import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

@NoArgsConstructor
@AllArgsConstructor
@Data
@Builder
public class PeopleDTO {
    private String firstName;
    private String lastName;
    private String email;
    private String dateOfBirth;
    private String phoneNumber;
    private Long guardianId;
    private PeopleDTO guardian;
    private List<PeopleDTO> children;
    private List<PeopleDTO> guardians;
    private Long cityId;
    private Long areaId;

}
