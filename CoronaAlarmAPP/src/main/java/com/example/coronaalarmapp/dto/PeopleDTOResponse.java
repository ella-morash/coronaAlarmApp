package com.example.coronaalarmapp.dto;

import com.example.coronaalarmapp.entity.language.Language;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@NoArgsConstructor
@AllArgsConstructor
@Data
@Builder

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
    private Language language;
}
