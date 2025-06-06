package com.example.mnraderbackend.controller;

import com.example.mnraderbackend.service.AnimalUserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

@RestController
@RequiredArgsConstructor
@RequestMapping("/user-animals")
public class AnimalUserController {

    private final AnimalUserService animalUserService;

    @PatchMapping(
            value = "/{animalId}",
            consumes = MediaType.MULTIPART_FORM_DATA_VALUE
    )
    public ResponseEntity<Void> updateAnimalUser(
            @PathVariable Long animalId,
            @RequestParam("animal") Integer animal,
            @RequestParam("breed") String breed,
            @RequestParam("gender") Integer gender,
            @RequestParam("age") Integer age,
            @RequestParam("detail") String detail,
            @RequestPart(value = "img", required = false) MultipartFile img

    ){
        animalUserService.updateAnimalUser(animalId, animal, breed, gender, age, detail, img);
        return ResponseEntity.ok().build();
    }
}
