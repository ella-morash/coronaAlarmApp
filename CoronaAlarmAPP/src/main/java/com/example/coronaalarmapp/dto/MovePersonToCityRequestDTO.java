package com.example.coronaalarmapp.dto;


import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Positive;

@NoArgsConstructor
@AllArgsConstructor
@Data
@Builder
public class MovePersonToCityRequestDTO {

    @NotNull
    @Positive
    private Long personId;
    @NotNull
    @Positive
    private Long fromCityId;
    @NotNull
    @Positive
    private Long toCityId;
}
