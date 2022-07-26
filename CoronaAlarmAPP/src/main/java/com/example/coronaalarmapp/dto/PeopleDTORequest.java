package com.example.coronaalarmapp.dto;


import com.sun.istack.Nullable;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.validator.constraints.Length;

import javax.validation.constraints.*;
import java.util.List;

@NoArgsConstructor
@AllArgsConstructor
@Data
@Builder
public class PeopleDTORequest {
    @NotBlank
    @Length(min=2,max=50)
    private String firstName;
    @NotBlank
    @Length(min=2,max=50)
    private String lastName;
    @Length(min=10,max=50)
    @NotBlank
    @Email(message = "incorrect format of email")
    private String email;
    @NotNull
    @Past
    private String dateOfBirth;
    @NotBlank
    @Pattern(regexp = "(\\(?([\\d \\-\\)\\–\\+\\/\\(]+)\\)?([ .\\-–\\/]?)([\\d]+))")
    private String phoneNumber;
    @Positive
    private Long guardianId;
    private List<PeopleDTORequest> children;
    private Long cityId;
    private Long areaId;

}
