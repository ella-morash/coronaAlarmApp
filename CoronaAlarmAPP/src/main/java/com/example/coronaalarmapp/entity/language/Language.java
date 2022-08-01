package com.example.coronaalarmapp.entity.language;


import com.example.coronaalarmapp.entity.severitystatus.SeverityStatus;
import com.fasterxml.jackson.annotation.JsonCreator;
import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.Arrays;

@AllArgsConstructor
@Getter
public enum Language {

    ENGLISH("en"),
    RUSSIAN("ru"),
    GERMAN("de");

    private final String languageType;


    public static Language getByType(String type) {
        if (type == null) {
            return null;
        }

        return Arrays.stream(Language.values())
                .filter(langType -> langType.getLanguageType().equals(type))
                .findFirst()
                .orElse(null);
    }
}
