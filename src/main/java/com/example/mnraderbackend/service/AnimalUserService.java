package com.example.mnraderbackend.service;

import com.example.mnraderbackend.common.convert.gender.Gender;
import com.example.mnraderbackend.common.convert.status.Status;
import com.example.mnraderbackend.common.model.AnimalUser;
import com.example.mnraderbackend.common.model.Breed;
import com.example.mnraderbackend.repository.AnimalUserRepository;
import com.example.mnraderbackend.repository.BreedRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.sql.Timestamp;

@Service
@RequiredArgsConstructor
public class AnimalUserService {

    private final AnimalUserRepository animalUserRepository;
    private final BreedRepository breedRepository;

    public void updateAnimalUser(Long animalId,
                                 Integer animal,
                                 String breedName,
                                 Integer genderCode,
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

        // update fields
        animalUser = AnimalUser.builder()
                .id(animalUser.getId())
                .breed(breed)
                .user(animalUser.getUser())
                .status(animalUser.getStatus()) // 그대로 유지
                .createdAt(animalUser.getCreatedAt()) // 유지
                .updatedAt(new Timestamp(System.currentTimeMillis()))
                .gender(gender)
                .detail(detail)
                .image(imageUrl)
                .build();

        animalUserRepository.save(animalUser);
    }
}
