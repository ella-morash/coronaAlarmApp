package com.example.coronaalarmapp.dto;


import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Data
@Builder
public class MovePersonToCityRequestDTO {

    private Long personId;
    private Long fromCityId;
    private Long toCityId;
}
