package com.example.mnraderbackend.domain.user.dto;

import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class MyRegionRequest {

    @NotNull(message = "city: {NotNull}")
    @Min(value = 1, message = "city: 최소 {value} 이상이어야 합니다")
    @Max(value = 17, message = "city: 최대 {value} 이하이어야 합니다")
    private Long city;

}
