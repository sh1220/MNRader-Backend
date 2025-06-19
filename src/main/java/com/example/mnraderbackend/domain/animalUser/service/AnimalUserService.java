package com.example.mnraderbackend.domain.animalUser.service;

import com.example.mnraderbackend.common.convert.animal_type.AnimalType;
import com.example.mnraderbackend.common.convert.gender.Gender;
import com.example.mnraderbackend.common.convert.statusAnimal.StatusAnimal;
import com.example.mnraderbackend.common.exception.AnimalException;
import com.example.mnraderbackend.common.exception.AnimalUserException;
import com.example.mnraderbackend.common.exception.AuthException;
import com.example.mnraderbackend.common.model.*;
import com.example.mnraderbackend.common.service.S3ImageService;
import com.example.mnraderbackend.domain.animal.dto.AnimalDetailResponse;
import com.example.mnraderbackend.domain.animal.repository.AnimalRepository;
import com.example.mnraderbackend.domain.animalUser.dto.AnimalUserResponse;
import com.example.mnraderbackend.domain.animalUser.repository.AnimalUserRepository;
import com.example.mnraderbackend.domain.breed.BreedRepository;
import com.example.mnraderbackend.domain.region.RegionRepository;
import com.example.mnraderbackend.domain.user.repository.ScrapRepository;
import com.example.mnraderbackend.domain.user.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.time.LocalDate;

import static com.example.mnraderbackend.common.response.status.BaseExceptionResponseStatus.*;

@Slf4j
@Service
@RequiredArgsConstructor
public class AnimalUserService {
    private final UserRepository userRepository;
    private final AnimalUserRepository animalUserRepository;
    private final BreedRepository breedRepository;
    private final S3ImageService s3ImageService;


    public AnimalUserResponse getAnimalUser(Long animalId) {
        AnimalUser animal = animalUserRepository.findById(animalId)
                .orElseThrow(() -> new AnimalUserException(ANIMAL_USER_NOT_FOUND));

        return AnimalUserResponse.builder()
                .animal(animal.getBreed().getAnimalType())
                .breed(animal.getBreed().getBreed())
                .gender(animal.getGender())
                .age(animal.getAge())
                .detail(animal.getDetail())
                .img(animal.getImage())
                .build();

    }

    @Transactional
    public void registerAnimalUser(
            Long userId,
            Long breedId,
            Integer genderCode,
            String name,
            Integer age,
            String detail,
            MultipartFile img
    ) {
        Breed breed = breedRepository.findById(breedId)
                .orElseThrow(() -> new AnimalUserException(BREED_NOT_FOUND));
        User user = getUser(userId);
        Gender gender = Gender.fromCode(genderCode);

        if (detail == null || detail.isBlank()) {
            detail = "상세 정보가 없습니다.";
        }

        String imageUrl = s3ImageService.saveAnimalUserImg(img);

        AnimalUser newAnimal = AnimalUser.builder()
                .breed(breed)
                .user(user)
                .gender(gender)
                .detail(detail)
                .image(imageUrl)
                .age(age)
                .name(name)
                .build();
        animalUserRepository.save(newAnimal);
    }

    @Transactional
    public void updateAnimalUser(
            Long userId,
            Long animalId,
            Long breed,
            Integer genderCode,
            String name,
            Integer age,
            String detail,
            MultipartFile img
    ) {
        AnimalUser animalUser = animalUserRepository.findById(animalId)
                .orElseThrow(() -> new AnimalUserException(ANIMAL_USER_NOT_FOUND));


        if (!breed.equals(animalUser.getBreed().getId())){
            Breed newBreed = breedRepository.findById(breed)
                    .orElseThrow(() -> new AnimalUserException(BREED_NOT_FOUND));
            animalUser.setBreed(newBreed);
        }

        if(genderCode != animalUser.getGender().getCode()){
            animalUser.setGender(Gender.fromCode(genderCode));
        }

        if (!name.equals(animalUser.getName())) {
            animalUser.setName(name);
        }

        if (!age.equals(animalUser.getAge())) {
            animalUser.setAge(age);
        }

        if (!detail.equals(animalUser.getDetail())) {
            if (detail.isBlank()) {
                detail = "상세 정보가 없습니다.";
            }
            animalUser.setDetail(detail);
        }

        if (img != null && !img.isEmpty()) {
            String imageUrl = s3ImageService.saveAnimalUserImg(img);
            animalUser.setImage(imageUrl);
        }

        animalUserRepository.save(animalUser);
    }




    private User getUser(long userId) {
        return userRepository.findById(userId)
                .orElseThrow(() -> new AuthException(USER_NOT_FOUND));
    }


}
