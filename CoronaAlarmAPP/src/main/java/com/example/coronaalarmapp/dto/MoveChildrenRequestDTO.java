package com.example.coronaalarmapp.dto;


import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.Positive;
import java.util.List;

@NoArgsConstructor
@AllArgsConstructor
@Data
@Builder
public class MoveChildrenRequestDTO {

    @Positive
    private Long fromGuardian;
    @Positive
    private Long toGuardian;
    private List<@Positive Long> childrenIds;
}
