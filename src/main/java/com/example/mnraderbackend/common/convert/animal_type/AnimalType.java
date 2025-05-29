package com.example.mnraderbackend.common.convert.animal_type;

import lombok.Getter;

@Getter
public enum AnimalType {
    DOG(1),       // 1로 매핑 수락
    CAT(2),     // 2로 매핑 거절
    OTHER(3);    // 3로 매핑 응답대기

    private final int code;

    AnimalType(int code) {
        this.code = code;
    }

    public static AnimalType fromCode(int code) {
        for (AnimalType animalType : AnimalType.values()) {
            if (animalType.getCode() == code) {
                return animalType;
            }
        }
        throw new IllegalArgumentException("알 수 없는 animal code 입니다." + code);
    }
}
