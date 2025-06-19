package com.example.mnraderbackend.domain.animalUser.dto;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.web.multipart.MultipartFile;

@Getter
@Setter
@NoArgsConstructor
public class AnimalUserUpdateRequest {

    @NotNull(message = "breed: {NotNull}")
    private Long breed;

    @NotNull(message = "gender: {NotNull}")
    private Integer gender;

    @NotBlank(message = "name: {NotBlank}")
    private String name;

    @NotNull(message = "age: {NotNull}")
    @Min(value = 0, message = "age: {Min}")
    private Integer age;

    private String detail;

    private MultipartFile img;

}
