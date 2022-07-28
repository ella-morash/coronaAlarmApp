package com.example.coronaalarmapp.entity.language;


import com.example.coronaalarmapp.entity.severitystatus.SeverityStatus;

import javax.persistence.AttributeConverter;
import javax.persistence.Converter;

@Converter(autoApply = true)
public class LanguageConvertor implements AttributeConverter<Language,Integer> {
    @Override
    public Integer convertToDatabaseColumn(Language language) {
        return  language.getLanguageId();
    }

    @Override
    public Language convertToEntityAttribute(Integer integer) {
        return Language.findByLanguageId(integer);
    }
}