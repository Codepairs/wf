package com.example.myapp.converter;

import jakarta.persistence.AttributeConverter;
import jakarta.persistence.Converter;

import java.time.LocalDate;

@Converter(autoApply = true)
public class NonLocalDateConverter implements AttributeConverter<LocalDate, String> {
    @Override
    public String convertToDatabaseColumn(java.time.LocalDate dateTime) {
        if (dateTime == null) {
            return null;
        }
        return dateTime.format(java.time.format.DateTimeFormatter.ofPattern("dd.MM.yyyy"));
    }


    @Override
    public LocalDate convertToEntityAttribute(String dbData) {
        if (dbData == null) {
            return null;
        }
        String[] dateData = dbData.split("\\.");
        int year = Integer.parseInt(dateData[0]);
        int month = Integer.parseInt(dateData[1]);
        int day = Integer.parseInt(dateData[2]);
        return LocalDate.of(day, month, year);
    }
}
