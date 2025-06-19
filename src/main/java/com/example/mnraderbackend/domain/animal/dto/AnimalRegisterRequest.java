package com.example.mnraderbackend.domain.animal.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.web.multipart.MultipartFile;

@Getter
@Setter
@NoArgsConstructor
public class AnimalRegisterRequest {

    @NotNull(message = "status: {NotNull}")
    private Long status;

    @NotBlank(message = "name: {NotBlank}")
    private String name;

    @NotBlank(message = "contact: {NotBlank}")
    private String contact;

    @NotNull(message = "animal: {NotNull}")
    private Long animal;

    @NotNull(message = "breed: {NotNull}")
    private Long breed;

    @NotNull(message = "gender: {NotNull}")
    private Long gender;

    @NotBlank(message = "address: {NotBlank}")
    private String address;

    @NotBlank(message = "occuredAt: {NotBlank}")
    private String occuredAt;

    private String detail;

    private MultipartFile img;

}
