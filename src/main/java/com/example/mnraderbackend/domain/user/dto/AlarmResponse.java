package com.example.mnraderbackend.domain.user.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.List;

@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class AlarmResponse {

    private Long lastAnimal;
    private List<AnimalDto> animal;


}