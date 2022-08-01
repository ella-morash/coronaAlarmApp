package com.example.coronaalarmapp.configuration;


import com.example.coronaalarmapp.entity.language.Language;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.convert.converter.Converter;
import org.springframework.format.FormatterRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class WebConfiguration implements WebMvcConfigurer {


    @Override
    public void addFormatters(FormatterRegistry registry) {
       registry.addConverter(new LanguageConverter());
    }
}

class LanguageConverter implements Converter<String, Language> {

    @Override
    public Language convert(String source) {
        return Language.getByType(source);
    }
}
