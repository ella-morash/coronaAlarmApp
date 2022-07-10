package com.example.coronaalarmapp.dto;


import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.validator.constraints.Length;

import javax.validation.constraints.NotBlank;
import java.util.List;

@NoArgsConstructor
@AllArgsConstructor
@Data
@Builder
public class AreaDTO {
    private Long areaId;
    @NotBlank
    @Length(min=3,max=50)
    private String areaName;
    @NotBlank
    @Length(min=2,max=2)
    private String areaCode;

    private List<Long> citiesId;
}
