package com.example.coronaalarmapp.dto;


import com.example.coronaalarmapp.entity.severitystatus.SeverityStatus;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.validator.constraints.Length;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Positive;

@NoArgsConstructor
@AllArgsConstructor
@Data
@Builder
public class CityDTO {
    private Long cityId;
    @NotBlank
    @Length(min=3,max=50)
    private String cityName;
    @NotNull
    @Positive
    private Long areaId;
    private SeverityStatus status;
}
