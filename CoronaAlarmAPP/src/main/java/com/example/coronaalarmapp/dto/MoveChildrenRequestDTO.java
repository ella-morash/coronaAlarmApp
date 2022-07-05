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
public class MoveChildrenRequestDTO {

    private Long fromGuardian;
    private Long toGuardian;
    private List<Long> childrenIds;
}
