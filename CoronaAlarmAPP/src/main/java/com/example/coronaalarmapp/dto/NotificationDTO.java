package com.example.coronaalarmapp.dto;


import com.example.coronaalarmapp.entity.severitystatus.SeverityStatus;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Data
@Builder
public class NotificationDTO {

    private Long areaId;
    private SeverityStatus status;

}
