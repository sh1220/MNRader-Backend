package com.example.mnraderbackend.common.convert.animal;

import lombok.Getter;

@Getter
public enum Animal {
    DOG(1),       // 1로 매핑 수락
    CAT(2),     // 2로 매핑 거절
    OTHER(3);    // 3로 매핑 응답대기

    private final int code;

    Animal(int code) {
        this.code = code;
    }

    public static Animal fromCode(int code) {
        for (Animal animal : Animal.values()) {
            if (animal.getCode() == code) {
                return animal;
            }
        }
        throw new IllegalArgumentException("알 수 없는 animal code 입니다." + code);
    }
}
