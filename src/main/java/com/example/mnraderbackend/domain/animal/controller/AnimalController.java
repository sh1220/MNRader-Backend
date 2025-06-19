package com.example.mnraderbackend.domain.animal.controller;

import com.example.mnraderbackend.common.argument_resolver.PreAccessToken;
import com.example.mnraderbackend.common.argument_resolver.PreAuthorize;
import com.example.mnraderbackend.common.exception.AnimalException;
import com.example.mnraderbackend.common.exception.UserException;
import com.example.mnraderbackend.common.response.BaseResponse;
import com.example.mnraderbackend.domain.animal.dto.AnimalDetailResponse;
import com.example.mnraderbackend.domain.animal.dto.AnimalRegisterRequest;
import com.example.mnraderbackend.domain.animal.service.AnimalService;
import com.example.mnraderbackend.domain.user.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import static com.example.mnraderbackend.common.response.status.BaseExceptionResponseStatus.*;
import static com.example.mnraderbackend.common.util.BindingResultUtils.getErrorMessages;

@RestController
@RequestMapping("/animals")
@RequiredArgsConstructor
public class AnimalController {

    private final AnimalService animalService;


    // 동물 상세 조회
    @GetMapping("/detail/{animalId}")
    public BaseResponse<AnimalDetailResponse> getAnimalDetail(
            @PreAuthorize Long userId,
            @PathVariable Long animalId
    ) {
        return new BaseResponse<>(ANIMAL_DETAIL_SUCCESS, animalService.getAnimalDetail(userId, animalId));
    }

    // 실종 동물 / 동물 목격 등록
    @PostMapping(value = "/register", consumes = "multipart/form-data")
    public BaseResponse<Void> registerAnimal(
            @PreAuthorize Long userId,
            @Validated @ModelAttribute AnimalRegisterRequest request,
            BindingResult bindingResult
    ) {
        if (bindingResult.hasErrors()) {
            throw new AnimalException(INVALID_ANIMAL_VALUE, getErrorMessages(bindingResult));
        }

        animalService.registerAnimal(
                userId,
                request.getStatus(),
                request.getName(),
                request.getContact(),
                request.getBreed(),
                request.getGender(),
                request.getAddress(),
                request.getOccuredAt(),
                request.getDetail(),
                request.getImg()
                );
        return new BaseResponse<>(ANIMAL_REGISTER_SUCCESS);
    }

    // 동물 스크랩 등록
    @PostMapping("/scrap/{animalId}")
    public BaseResponse<Void> scrapAnimal(
            @PreAuthorize Long userId,
            @PathVariable Long animalId
    ) {
        animalService.scrapAnimal(userId, animalId);
        return new BaseResponse<>(ANIMAL_SCRAP_SUCCESS);
    }

    // 동물 스크랩 취소
    @DeleteMapping("/scrap/{animalId}")
    public BaseResponse<Void> cancelScrap(
            @PreAuthorize Long userId,
            @PathVariable Long animalId
    ) {
        animalService.cancelScrap(userId, animalId);
        return new BaseResponse<>(ANIMAL_SCRAP_CANCEL_SUCCESS);
    }



}