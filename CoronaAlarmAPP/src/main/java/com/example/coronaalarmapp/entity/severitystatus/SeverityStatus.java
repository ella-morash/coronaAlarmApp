package com.example.coronaalarmapp.entity.severitystatus;

import com.fasterxml.jackson.annotation.JsonCreator;
import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.Arrays;

@AllArgsConstructor
@Getter
public enum SeverityStatus {
    GREEN(1,"green"),
    YELLOW(2,"yellow"),
    ORANGE(3,"orange"),
    RED(4,"red");

    private final Integer statusId;
    private final String externalStatusId;

    public static SeverityStatus findByStatusId(Integer statusId) {
        if (statusId == null) {
            return null;
        }

        return Arrays.stream(SeverityStatus.values())
                .filter(x -> x.getStatusId().equals(statusId))
                .findFirst()
                .orElse(null);
    }
    @JsonCreator
    public static SeverityStatus findByExternalStatusId( String externalStatusId) {
        if (externalStatusId == null) {
            return null;
        }

        return Arrays.stream(SeverityStatus.values())
                .filter(x -> x.externalStatusId.equals(externalStatusId))
                .findFirst()
                .orElse(null);
    }
}
