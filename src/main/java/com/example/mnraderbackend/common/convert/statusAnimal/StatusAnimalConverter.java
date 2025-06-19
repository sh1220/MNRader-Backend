package com.example.mnraderbackend.common.convert.statusAnimal;

import jakarta.persistence.AttributeConverter;
import jakarta.persistence.Converter;

@Converter(autoApply = true) // 자동으로 모든 관련 필드에 적용
public class StatusAnimalConverter implements AttributeConverter<StatusAnimal, Integer> {

    @Override
    public Integer convertToDatabaseColumn(StatusAnimal attribute) {
        if (attribute == null) {
            return null;
        }
        return attribute.getCode(); // Enum -> DB 정수값
    }

    @Override
    public StatusAnimal convertToEntityAttribute(Integer dbData) {
        if (dbData == null) {
            return null;
        }
        return StatusAnimal.fromCode(dbData); // DB 정수값 -> Enum
    }
}
