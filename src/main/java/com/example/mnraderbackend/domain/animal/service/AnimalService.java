package com.example.mnraderbackend.domain.animal.service;

import com.example.mnraderbackend.common.convert.gender.Gender;
import com.example.mnraderbackend.common.convert.statusAnimal.StatusAnimal;
import com.example.mnraderbackend.common.exception.AnimalException;
import com.example.mnraderbackend.common.exception.AuthException;
import com.example.mnraderbackend.common.model.*;
import com.example.mnraderbackend.common.service.S3ImageService;
import com.example.mnraderbackend.domain.animal.dto.AnimalDetailResponse;
import com.example.mnraderbackend.domain.animal.dto.AnimalRegisterRequest;
import com.example.mnraderbackend.domain.animal.repository.AnimalRepository;
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
public class AnimalService {
    private final UserRepository userRepository;
    private final AnimalRepository animalRepository;
    private final RegionRepository regionRepository;
    private final ScrapRepository scrapRepository;
    private final BreedRepository breedRepository;
    private final S3ImageService s3ImageService;


    public AnimalDetailResponse getAnimalDetail(Long userId, Long animalId) {
        Animal animal = animalRepository.findById(animalId)
                .orElseThrow(() -> new AnimalException(ANIMAL_NOT_FOUND));
        Boolean isScrapped = scrapRepository.existsByUserIdAndAnimalId(userId, animalId);

        return AnimalDetailResponse.builder()
                .status(animal.getStatus().name())
                .img(animal.getImage())
                .breed(animal.getBreed().getBreed())
                .address(animal.getAddress())
                .gender(animal.getGender().name())
                .occurredAt(animal.getOccurredAt().toString())
                .contact(animal.getPhone())
                .detail(animal.getDetail())
                .isScrapped(isScrapped)
                .build();
    }

    @Transactional
    public void registerAnimal(
            Long userId,
            Integer statusCode,
            String name,
            String contact,
            Long breedId,
            Integer genderCode,
            String address,
            LocalDate occuredAt,
            String detail,
            MultipartFile img
    ) {
        Breed breed = breedRepository.findById(breedId)
                .orElseThrow(() -> new AnimalException(BREED_NOT_FOUND));
        User user = getUser(userId);
        String regionString = address.split(" ")[0];
        Region region = regionRepository.findByCity(regionString)
                .orElseThrow(() -> new AnimalException(REGION_NOT_FOUND));
        StatusAnimal statusAnimal = StatusAnimal.fromCode(statusCode);
        Gender gender = Gender.fromCode(genderCode);


        if (img == null || img.isEmpty()) {
            throw new AnimalException(INVALID_IMG);
        }
        if (detail == null || detail.isBlank()) {
            detail = "상세 정보가 없습니다.";
        }
        String imageUrl = s3ImageService.saveAnimalImg(img);

        Animal newAnimal = Animal.builder()
                .breed(breed)
                .user(user)
                .region(region)
                .status(statusAnimal)
                .address(address)
                .gender(gender)
                .occurredAt(occuredAt)
                .phone(contact)
                .name(name)
                .detail(detail)
                .image(imageUrl)
                .build();
        animalRepository.save(newAnimal);
    }


    @Transactional
    public void scrapAnimal(Long userId, Long animalId) {
        User user = getUser(userId);
        Animal animal = animalRepository.findById(animalId)
                .orElseThrow(() -> new AnimalException(ANIMAL_NOT_FOUND));

        if (scrapRepository.existsByUserIdAndAnimalId(userId, animalId)) {
            throw new AnimalException(ALREADY_SCRAPPED);
        }

        Scrap scrap = Scrap.builder()
                .user(user)
                .animal(animal)
                .build();
        scrapRepository.save(scrap);
    }

    @Transactional
    public void cancelScrap(Long userId, Long animalId) {
        Scrap scrap = scrapRepository.findByUserIdAndAnimalId(userId, animalId)
                .orElseThrow(() -> new AnimalException(SCRAP_NOT_FOUND));
        scrapRepository.delete(scrap);
    }

    private User getUser(long userId) {
        return userRepository.findById(userId)
                .orElseThrow(() -> new AuthException(USER_NOT_FOUND));
    }


}
