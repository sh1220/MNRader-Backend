package com.example.mnraderbackend.dto;


import com.example.mnraderbackend.common.convert.animal_type.AnimalType;
import com.example.mnraderbackend.common.model.AnimalUser;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

@Getter
@AllArgsConstructor
@Builder
public class AnimalUserResponse {
    private Long id;
    private AnimalType animal;
    private String breed;
    private int gender;
    private int age;
    private String detail;
    private String img;

    public static AnimalUserResponse from(AnimalUser entity) {
        return AnimalUserResponse.builder()
                .id(entity.getId())
                .animal(entity.getBreed().getAnimalType())
                .breed(entity.getBreed().getBreed())
                .gender(entity.getGender().getCode())
                .age(entity.getAge())
                .detail(entity.getDetail())
                .img(entity.getImage())
                .build();
    }


}
