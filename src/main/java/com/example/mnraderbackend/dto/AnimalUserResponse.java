package com.example.mnraderbackend.dto;


import com.example.mnraderbackend.common.convert.animal_type.AnimalType;
import com.example.mnraderbackend.common.model.AnimalUser;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class AnimalUserResponse {
    private Long id;
    private AnimalType animal;
    private String breed;
    private int gender;
    private int age;
    private String detail;
    private String img;

    public static AnimalUserResponse from(AnimalUser entity) {
        return new AnimalUserResponse(
                entity.getId(),
                entity.getBreed().getAnimalType(),
                entity.getBreed().getBreed(),
                entity.getGender().getCode(),
                entity.getAge(),
                entity.getDetail(),
                entity.getImage()
        );
    }
}
