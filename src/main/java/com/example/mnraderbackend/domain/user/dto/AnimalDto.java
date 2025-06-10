package com.example.mnraderbackend.domain.user.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class AnimalDto {
    private Long animalId;
    private String status;
    private String img;
    private String name;
    private Long city;
    private String gender;
    private String occurredAt;
}