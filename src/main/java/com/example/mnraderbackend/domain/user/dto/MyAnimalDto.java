package com.example.mnraderbackend.domain.user.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class MyAnimalDto {

    private Long animalUserId;
    private String img;
    private String name;

}
