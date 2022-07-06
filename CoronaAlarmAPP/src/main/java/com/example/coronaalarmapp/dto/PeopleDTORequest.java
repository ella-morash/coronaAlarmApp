package com.example.coronaalarmapp.dto;


import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.util.List;

@NoArgsConstructor
@AllArgsConstructor
@Data
@Builder
public class PeopleDTORequest {
    private String firstName;
    private String lastName;
    @NotNull
    @NotBlank
    @Email(message = "incorrect format of email")
    private String email;
    private String dateOfBirth;
    private String phoneNumber;
    private Long guardianId;
    private List<PeopleDTORequest> guardians;
    private Long cityId;
    private Long areaId;

}
