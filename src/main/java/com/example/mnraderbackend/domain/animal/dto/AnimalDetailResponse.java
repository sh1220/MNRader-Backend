package com.example.mnraderbackend.domain.animal.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.List;

@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class AnimalDetailResponse {
    private String status;
    private String img;
    private String breed;
    private String address;
    private String gender;
    private String occurredAt;
    private String contact;
    private String detail;
    private Boolean isScrapped;
}