package com.example.coronaalarmapp.dto;


import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@NoArgsConstructor
@AllArgsConstructor
@Data
@Builder
public class AreaDTO {
    private Long areaId;
    private String areaName;
    private List<Long> citiesId;
}
