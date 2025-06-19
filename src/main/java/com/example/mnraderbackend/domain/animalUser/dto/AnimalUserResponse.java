package com.example.mnraderbackend.domain.animalUser.dto;

import com.example.mnraderbackend.common.convert.animal_type.AnimalType;
import com.example.mnraderbackend.common.convert.gender.Gender;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class AnimalUserResponse {
    private AnimalType animal;
    private String breed;
    private Gender gender;
    private Integer age;
    private String detail;
    private String img;
}