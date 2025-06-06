package com.example.mnraderbackend.controller;

import com.example.mnraderbackend.service.AnimalUserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

@RestController
@RequiredArgsConstructor
@RequestMapping("/user-animals")
public class AnimalUserController {

    private final AnimalUserService animalUserService;

    @PatchMapping("/{animalId}")
    public ResponseEntity<Void> updateAnimalUser(
            @PathVariable Long animalId,
            @RequestPart("animal") Integer animalType,
            @RequestPart("breed") String breedName,
            @RequestPart("gender") Integer gender,
            @RequestPart("name") String name,
            @RequestPart("age") Integer age,
            @RequestPart("detail") String detail,
            @RequestPart(value = "img", required = false) MultipartFile imageFile
    ) {
        animalUserService.updateAnimalUser(
                animalId, animalType, breedName, gender, name, age, detail, imageFile
        );
        return ResponseEntity.ok().build();
    }
}
