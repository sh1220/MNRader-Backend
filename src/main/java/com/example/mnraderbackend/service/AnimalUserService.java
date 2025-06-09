package com.example.mnraderbackend.service;
import com.example.mnraderbackend.common.convert.animal_type.AnimalType;
import com.example.mnraderbackend.common.convert.gender.Gender;
import com.example.mnraderbackend.common.convert.status.Status;
import com.example.mnraderbackend.common.model.AnimalUser;
import com.example.mnraderbackend.common.model.Breed;
import com.example.mnraderbackend.common.model.User;
import com.example.mnraderbackend.dto.AnimalUserResponse;
import com.example.mnraderbackend.repository.AnimalUserRepository;
import com.example.mnraderbackend.repository.BreedRepository;
import com.example.mnraderbackend.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Transactional
public class AnimalUserService {

    private final AnimalUserRepository animalUserRepository;
    private final BreedRepository breedRepository;
    private final UserRepository userRepository;

    public List<AnimalUserResponse> getAnimalUsersByEmail(String email) {
        List<AnimalUser> animals = animalUserRepository.findByUserEmail(email);
        return animals.stream()
                .map(AnimalUserResponse::from)
                .collect(Collectors.toList());
    }

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

    public void createAnimalUser(
            Integer animal,
            String breedName,
            Integer genderCode,
            String name,
            Integer age,
            String detail,
            Integer statusCode,
            MultipartFile image
    ) {
        // 임시 테스트 사용자
        User user = userRepository.findByEmail("123@konkuk.ac.kr")
                .orElseThrow(() -> new IllegalArgumentException("User not found"));

        AnimalType animalType = AnimalType.fromCode(animal);


        Breed breed = breedRepository.findByAnimalTypeAndBreed(animalType, breedName)
                .orElseThrow(() -> new IllegalArgumentException("Breed not found"));

        Status status = Status.fromCode(statusCode); // enum 매핑

        AnimalUser newPet = AnimalUser.builder()
                .user(user)
                .breed(breed)
                .gender(Gender.fromCode(genderCode))
                .name(name)
                .age(age)
                .detail(detail)
                .status(status)
                .image(image != null ? image.getOriginalFilename() : null) // 실제 업로드 로직 없음
                .createdAt(Timestamp.valueOf(LocalDateTime.now()))
                .updatedAt(Timestamp.valueOf(LocalDateTime.now()))
                .build();

        animalUserRepository.save(newPet);
    }

}
