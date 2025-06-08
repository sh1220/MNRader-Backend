package com.example.mnraderbackend.service;

import com.example.mnraderbackend.common.convert.gender.Gender;
import com.example.mnraderbackend.common.model.AnimalUser;
import com.example.mnraderbackend.common.model.Breed;
import com.example.mnraderbackend.dto.AnimalUserResponse;
import com.example.mnraderbackend.repository.AnimalUserRepository;
import com.example.mnraderbackend.repository.BreedRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.sql.Timestamp;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Transactional
public class AnimalUserService {

    private final AnimalUserRepository animalUserRepository;
    private final BreedRepository breedRepository;

    // 제가 구현하는 부분이 아닌것 같아서 주석처리했습니다
    // 확인되면 merge 전에 삭제 하겠습니다.

    //    public List<AnimalUserResponse> getAnimalUsersByEmail(String email) {
//        List<AnimalUser> animals = animalUserRepository.findByUserEmail(email);
//        return animals.stream()
//                .map(AnimalUserResponse::from)
//                .collect(Collectors.toList());
//    }

    public AnimalUser updateAnimalUser(Long animalId,
                                 Integer animal,
                                 String breedName,
                                 Integer genderCode,
                                 Integer age,
                                 String detail,
                                 MultipartFile imageFile) {

        AnimalUser animalUser = animalUserRepository.findById(animalId)
                .orElseThrow(() -> new IllegalArgumentException("AnimalUser not found"));

        Breed breed = breedRepository.findByBreed(breedName)
                .orElseThrow(() -> new IllegalArgumentException("Breed not found"));

        Gender gender = switch (genderCode) {
            case 1 -> Gender.MALE;
            case 2 -> Gender.FEMALE;
            default -> Gender.UNKNOWN;
        };

        // todo S3 없이: 기존 이미지 유지 or 임시 경로 사용
        String imageUrl = (imageFile != null && !imageFile.isEmpty())
                ? "/images/" + imageFile.getOriginalFilename()
                : animalUser.getImage();

        // update fields (jpa dirty checking)
        animalUser.setBreed(breed);
        animalUser.setGender(gender);
        animalUser.setAge(age);
        animalUser.setDetail(detail);
        animalUser.setImage(imageUrl);
        animalUser.setUpdatedAt(new Timestamp(System.currentTimeMillis()));

        return animalUser;
    }
}
