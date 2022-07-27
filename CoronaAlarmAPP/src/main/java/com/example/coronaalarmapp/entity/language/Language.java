package com.example.coronaalarmapp.entity.language;


import com.example.coronaalarmapp.entity.severitystatus.SeverityStatus;
import com.fasterxml.jackson.annotation.JsonCreator;
import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.Arrays;

@AllArgsConstructor
@Getter
public enum Language {

    ENGLISH(1,"en"),
    RUSSIAN(2,"ru"),
    GERMAN(3,"de");

    private final Integer languageId;
    private final String externalLanguageId;


    public static Language findByLanguageId(Integer languageId) {
        if (languageId == null) {
            return null;
        }

        return Arrays.stream(Language.values())
                .filter(x -> x.languageId.equals(languageId))
                .findFirst()
                .orElse(null);
    }
    @JsonCreator
    public static Language findByExternalLanguageId( String externalLanguageId) {
        if (externalLanguageId == null) {
            return null;
        }

        return Arrays.stream(Language.values())
                .filter(x -> x.externalLanguageId.equals(externalLanguageId))
                .findFirst()
                .orElse(null);
    }
}
