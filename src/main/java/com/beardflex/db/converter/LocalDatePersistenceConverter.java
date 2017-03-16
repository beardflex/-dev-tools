package com.beardflex.db.converter;

import javax.persistence.AttributeConverter;
import javax.persistence.Converter;
import java.sql.Date;
import java.time.LocalDate;

/**
 * Created by David on 16/03/2017.
 */
@Converter
public class LocalDatePersistenceConverter implements AttributeConverter<LocalDate, java.sql.Date> {
    @Override
    public Date convertToDatabaseColumn(LocalDate attribute) {
        if(attribute != null) {
            return Date.valueOf(attribute);
        }
        return null;
    }

    @Override
    public LocalDate convertToEntityAttribute(Date dbData) {
        if(dbData != null) {
            return dbData.toLocalDate();
        }
        return null;
    }
}
