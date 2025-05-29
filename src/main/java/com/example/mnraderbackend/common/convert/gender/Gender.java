package com.example.mnraderbackend.common.convert.gender;

import lombok.Getter;

@Getter
public enum Gender {
    MALE(1),       // 1로 매핑 수락
    FEMALE(2),     // 2로 매핑 거절
    UNKNOWN(3);    // 3로 매핑 응답대기

    private final int code;

    Gender(int code) {
        this.code = code;
    }

    public static Gender fromCode(int code) {
        for (Gender gender : Gender.values()) {
            if (gender.getCode() == code) {
                return gender;
            }
        }
        throw new IllegalArgumentException("알 수 없는 gender code 입니다." + code);
    }
}
