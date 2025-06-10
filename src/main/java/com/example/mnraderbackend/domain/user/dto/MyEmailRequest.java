package com.example.mnraderbackend.domain.user.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class MyEmailRequest {

    @NotBlank(message = "email: {NotBlank}")
    private String email;

}
