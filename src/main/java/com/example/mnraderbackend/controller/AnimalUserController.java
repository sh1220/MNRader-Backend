package com.example.mnraderbackend.controller;

import com.example.mnraderbackend.common.response.BaseResponse;
import com.example.mnraderbackend.common.response.status.BaseExceptionResponseStatus;
import com.example.mnraderbackend.dto.AnimalUserResponse;
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

    // 제가 구현하는 부분이 아닌것 같아서 주석처리했습니다
    // 확인되면 merge 전에 삭제 하겠습니다.

//    @GetMapping("/by-email")
//    public ResponseEntity<BaseResponse<List<AnimalUserResponse>>> getAnimalUsersByEmail(
//            @RequestParam String email) {
//        List<AnimalUserResponse> animals = animalUserService.getAnimalUsersByEmail(email);
//        return ResponseEntity.status(200).body(
//                new BaseResponse<>(2002, 200, "조회 성공!", animals)
//        );
//    }

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
        try {
            var updated = animalUserService.updateAnimalUser(animalId, animal, breed, gender, age, detail, img);

            return ResponseEntity.status(BaseExceptionResponseStatus.MY_UPDATE_ANIMAL_SUCCESS.getStatus())
                    .body(new BaseResponse<>(BaseExceptionResponseStatus.MY_UPDATE_ANIMAL_SUCCESS, AnimalUserResponse.from(updated)));

        } catch (RuntimeException e) {
            return ResponseEntity.status(BaseExceptionResponseStatus.MY_UPDATE_ANIMAL_FAIL.getStatus())
                    .body(new BaseResponse<>(BaseExceptionResponseStatus.MY_UPDATE_ANIMAL_FAIL, null));
        }
    }
}
