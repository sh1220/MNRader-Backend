package com.example.mnraderbackend.controller;

import com.example.mnraderbackend.dto.AnimalUserResponse;
import com.example.mnraderbackend.dto.BaseResponse;
import com.example.mnraderbackend.service.AnimalUserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/user-animals")
public class AnimalUserController {

    private final AnimalUserService animalUserService;

    @GetMapping("/by-email")
    public ResponseEntity<BaseResponse<List<AnimalUserResponse>>> getAnimalUsersByEmail(
            @RequestParam String email) {
        List<AnimalUserResponse> animals = animalUserService.getAnimalUsersByEmail(email);
        return ResponseEntity.status(200).body(
                new BaseResponse<>(2002, 200, "조회 성공!", animals)
        );
    }

    @PatchMapping(
            value = "/{animalId}",
            consumes = MediaType.MULTIPART_FORM_DATA_VALUE
    )
    public ResponseEntity<BaseResponse<AnimalUserResponse>> updateAnimalUser(
            @PathVariable Long animalId,
            @RequestParam("animal") Integer animal,
            @RequestParam("breed") String breed,
            @RequestParam("gender") Integer gender,
            @RequestParam("age") Integer age,
            @RequestParam("detail") String detail,
            @RequestPart(value = "img", required = false) MultipartFile img

    ){
        var updated = animalUserService.updateAnimalUser(animalId, animal, breed, gender, age, detail, img);
        return ResponseEntity.status(201).body(
                new BaseResponse<>(2002, 201, "저장 성공!", AnimalUserResponse.from(updated))
        );
    }

    @PostMapping(consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<Void> createAnimalUser(
            @RequestPart("animal") String animal,
            @RequestPart("breed") String breed,
            @RequestPart("gender") String gender,
            @RequestPart("name") String name,
            @RequestPart("age") String age,
            @RequestPart("detail") String detail,
            @RequestPart("status") String status, // 추가
            @RequestPart(value = "img", required = false) MultipartFile img
    ) {
        animalUserService.createAnimalUser(
                Integer.parseInt(animal),
                breed,
                Integer.parseInt(gender),
                name,
                Integer.parseInt(age),
                detail,
                Integer.parseInt(status), // 추가
                img
        );
        return ResponseEntity.ok().build();
    }

}
