package com.example.mnraderbackend.domain.user.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.*;

@Getter
@Builder
@AllArgsConstructor
public class MyEmailResponse {

    private String accessToken;
    private String refreshToken;

}
