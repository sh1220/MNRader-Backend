package com.example.mnraderbackend.common.convert.statusAnimal;

import lombok.Getter;

@Getter
public enum StatusAnimal {
    LOST(1),       // 1로 매핑 수락
    PROTECTED(2),     // 2로 매핑 거절
    SIGHTING(3),
    NONE(4);// 3로 매핑 응답대기

    private final int code;

    StatusAnimal(int code) {
        this.code = code;
    }

    public static StatusAnimal fromCode(int code) {
        for (StatusAnimal statusAnimal : StatusAnimal.values()) {
            if (statusAnimal.getCode() == code) {
                return statusAnimal;
            }
        }
        throw new IllegalArgumentException("알 수 없는 status code 입니다." + code);
    }
}
