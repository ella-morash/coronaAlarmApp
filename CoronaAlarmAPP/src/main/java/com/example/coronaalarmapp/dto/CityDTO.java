package com.example.coronaalarmapp.dto;


import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Data
@Builder
public class CityDTO {
    private Long cityId;
    private String cityName;
    private Long areaId;
}
