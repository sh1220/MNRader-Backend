package com.example.mnraderbackend.domain.animal.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.web.multipart.MultipartFile;

import java.time.LocalDate;

@Getter
@Setter
@NoArgsConstructor
public class AnimalRegisterRequest {

    @NotNull(message = "status: {NotNull}")
    private Integer status;

    @NotBlank(message = "name: {NotBlank}")
    private String name;

    @NotBlank(message = "contact: {NotBlank}")
    private String contact;

    @NotNull(message = "breed: {NotNull}")
    private Long breed;

    @NotNull(message = "gender: {NotNull}")
    private Integer gender;

    @NotBlank(message = "address: {NotBlank}")
    private String address;

    @NotNull(message = "occuredAt: {NotNull}")
    private LocalDate occuredAt;

    private String detail;

    private MultipartFile img;

}
