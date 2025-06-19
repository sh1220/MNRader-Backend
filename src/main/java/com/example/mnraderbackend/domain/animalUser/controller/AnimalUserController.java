package com.example.mnraderbackend.domain.animalUser.controller;

import com.example.mnraderbackend.common.argument_resolver.PreAuthorize;
import com.example.mnraderbackend.common.exception.AnimalException;
import com.example.mnraderbackend.common.exception.AnimalUserException;
import com.example.mnraderbackend.common.response.BaseResponse;
import com.example.mnraderbackend.domain.animal.dto.AnimalDetailResponse;
import com.example.mnraderbackend.domain.animal.dto.AnimalRegisterRequest;
import com.example.mnraderbackend.domain.animal.service.AnimalService;
import com.example.mnraderbackend.domain.animalUser.dto.AnimalUserRegisterRequest;
import com.example.mnraderbackend.domain.animalUser.dto.AnimalUserResponse;
import com.example.mnraderbackend.domain.animalUser.dto.AnimalUserUpdateRequest;
import com.example.mnraderbackend.domain.animalUser.service.AnimalUserService;
import lombok.RequiredArgsConstructor;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import static com.example.mnraderbackend.common.response.status.BaseExceptionResponseStatus.*;
import static com.example.mnraderbackend.common.util.BindingResultUtils.getErrorMessages;

@RestController
@RequestMapping("/user-animals")
@RequiredArgsConstructor
public class AnimalUserController {

    private final AnimalUserService animalUserService;


    // 유저 동물 상세 조회 (GET)
    @GetMapping("/{animalId}")
    public BaseResponse<AnimalUserResponse> getAnimalUser(
            @PathVariable Long animalId
    ) {
        return new BaseResponse<>(ANIMAL_USER_DETAIL_SUCCESS, animalUserService.getAnimalUser(animalId));
    }

    // 유저 동물 등록 (POST)
    @PostMapping
    public BaseResponse<Void> registerAnimal(
            @PreAuthorize Long userId,
            @Validated @ModelAttribute AnimalUserRegisterRequest request,
            BindingResult bindingResult
    ) {
        if (bindingResult.hasErrors()) {
            throw new AnimalUserException(INVALID_ANIMAL_USER_VALUE, getErrorMessages(bindingResult));
        }
        animalUserService.registerAnimalUser(userId, request.getBreed(), request.getGender(),
                request.getName(), request.getAge(), request.getDetail(), request.getImg());
        return new BaseResponse<>(ANIMAL_USER_REGISTER_SUCCESS);
    }

    // 유저 동물 정보 수정 (PATCH)
    @PatchMapping("/{animalId}")
    public BaseResponse<Void> updateAnimalUser(
            @PreAuthorize Long userId,
            @PathVariable Long animalId,
            @Validated @ModelAttribute AnimalUserUpdateRequest request
    ) {
        animalUserService.updateAnimalUser(userId, animalId, request.getBreed(), request.getGender(),
                request.getName(), request.getAge(), request.getDetail(), request.getImg());
        return new BaseResponse<>(ANIMAL_USER_UPDATE_SUCCESS);
    }




}