package com.example.coronaalarmapp.entity.severitystatus;

import javax.persistence.AttributeConverter;
import javax.persistence.Converter;

@Converter(autoApply = true)
public class StatusConverter implements AttributeConverter<SeverityStatus,Integer> {
    @Override
    public Integer convertToDatabaseColumn(SeverityStatus status) {
        return status == null ? null : status.getStatusId();
    }

    @Override
    public SeverityStatus convertToEntityAttribute(Integer integer) {
        return integer == null ? null : SeverityStatus.findByStatusId(integer);
    }

}