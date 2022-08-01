package com.example.coronaalarmapp.entity.language;




import javax.persistence.AttributeConverter;
import javax.persistence.Converter;

@Converter(autoApply = true)
public class LanguageConverter implements AttributeConverter<Language,String> {

    @Override
    public String convertToDatabaseColumn(Language language) {
        return language == null ? null : language.getLanguageType();
    }

    @Override
    public Language convertToEntityAttribute(String s) {
        return s == null ? null : Language.getByType(s);
    }
}
