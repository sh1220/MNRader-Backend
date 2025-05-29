package com.example.mnraderbackend.common.convert.animal_type;

import jakarta.persistence.AttributeConverter;
import jakarta.persistence.Converter;

@Converter(autoApply = true) // 자동으로 모든 관련 필드에 적용
public class AnimalTypeConverter implements AttributeConverter<AnimalType, Integer> {

    @Override
    public Integer convertToDatabaseColumn(AnimalType attribute) {
        if (attribute == null) {
            return null;
        }
        return attribute.getCode(); // Enum -> DB 정수값
    }

    @Override
    public AnimalType convertToEntityAttribute(Integer dbData) {
        if (dbData == null) {
            return null;
        }
        return AnimalType.fromCode(dbData); // DB 정수값 -> Enum
    }
}
