package com.example.mnraderbackend.domain.user.service;

import com.example.mnraderbackend.common.argument_resolver.PreAuthorize;
import com.example.mnraderbackend.common.convert.statusAnimal.StatusAnimal;
import com.example.mnraderbackend.common.exception.AlarmException;
import com.example.mnraderbackend.common.exception.AnimalException;
import com.example.mnraderbackend.common.exception.AuthException;
import com.example.mnraderbackend.common.jwt.JwtProvider;
import com.example.mnraderbackend.common.model.*;
import com.example.mnraderbackend.domain.animal.repository.AnimalRepository;
import com.example.mnraderbackend.domain.animalUser.repository.AnimalUserRepository;
import com.example.mnraderbackend.domain.breed.BreedRepository;
import com.example.mnraderbackend.domain.region.RegionRepository;
import com.example.mnraderbackend.domain.user.dto.*;
import com.example.mnraderbackend.domain.user.repository.ScrapRepository;
import com.example.mnraderbackend.domain.user.repository.UserRepository;
import com.google.firebase.messaging.*;
import jakarta.validation.constraints.NotBlank;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

import static com.example.mnraderbackend.common.response.status.BaseExceptionResponseStatus.*;

@Slf4j
@Service
@RequiredArgsConstructor
public class UserService {
    private final UserRepository userRepository;
    private final BreedRepository breedRepository;
    private final AnimalRepository animalRepository;
    private final AnimalUserRepository animalUserRepository;
    private final RegionRepository regionRepository;
    private final ScrapRepository scrapRepository;
    private final JwtProvider jwtProvider;

    @Transactional
    public void registerFcmToken(Long userId, String fcmToken) {
        User user = getUser(userId);
        user.setFcmToken(fcmToken);
        // userRepository.save(user); <- 영속성 컨텍스트에서 자동 업데이트 됨
    }

    public void sendAlarm(Region region, Integer status) {

        if(status == StatusAnimal.LOST.getCode()) {
            sendLostAlarm(region);
        } else if (status == StatusAnimal.SIGHTING.getCode()) {
            sendSightAlarm(region);
        } else {
            throw new AnimalException(ANIMAL_STATUS_NOT_FOUND);
        }
    }
    public void sendLostAlarm(Region region) {
        sendFirebaseMessage("동물 실종", "현 지역에서 동물이 실종되었습니다.", region);
    }

    public void sendSightAlarm(Region region) {
        sendFirebaseMessage("유기동물 발견", "현 지역에서 동물이 발견되었습니다.", region);
    }
    private void sendFirebaseMessage(String title, String content, Region region) {
        List<String> registrationTokens = userRepository.findFcmTokensByRegion(region);
        MulticastMessage message = MulticastMessage.builder()
                .putData("title", title)
                .putData("content", content)
                .addAllTokens(registrationTokens)
                .build();

        try {
            BatchResponse response = FirebaseMessaging.getInstance().sendMulticast(message);
            if (response.getFailureCount() > 0) {
                List<SendResponse> responses = response.getResponses();
                for (int i = 0; i < responses.size(); i++) {
                    SendResponse sendResponse = responses.get(i);
                    if (!sendResponse.isSuccessful()) {
                        log.error("Firebase 메시지 전송 실패: {}", sendResponse.getException().getMessage());
                    }
                }
            } else {
                log.info("Firebase 메시지 전송 성공");
            }
        } catch (FirebaseMessagingException e) {
            log.error("Firebase 메시지 전송 실패: {}", e.getMessage());
        }
    }

    public HomeResponse home(Long userId, Long breedId, Long cityId) {
        // 사용자 정보 조회
        User user = getUser(userId);

        // 마지막 동물 정보 조회
        Animal lastAnimal = animalRepository.findTopByOrderByIdDesc()
                .orElseThrow(() -> new AnimalException(ANIMAL_NULL));

        // city가 지정되어 있으면 해당 region을, 아니면 user의 region을 사용
        Region targetRegion = (cityId != null)
                ? regionRepository.findById(cityId).orElseThrow(() -> new AuthException(REGION_NOT_FOUND))
                : user.getRegion();

        // 해당 지역의 동물 목록 조회
        List<Animal> animals;
        if (breedId != null) {
            Breed breed = breedRepository.findById(breedId)
                    .orElseThrow(() -> new AnimalException(BREED_NOT_FOUND));
            animals = animalRepository.findByRegionAndBreed(targetRegion, breed);
        } else {
            animals = animalRepository.findByRegion(targetRegion);
        }

        if (animals.isEmpty()) {
            throw new AnimalException(ANIMAL_NULL);
        }

        return HomeResponse.builder()
                .lastAnimal(lastAnimal.getId())
                .animal(animals.stream()
                        .map(animal -> AnimalDto.builder()
                                .animalId(animal.getId())
                                .status(animal.getStatus().name())
                                .img(animal.getImage())
                                .breed(animal.getBreed().getBreed())
                                .city(animal.getRegion().getId())
                                .gender(animal.getGender().name())
                                .occurredAt(animal.getOccurredAt().toString())
                                .build())
                        .toList())
                .build();

    }

    public AlarmResponse getAlarmPage(Long userId, Long lastAnimalId) {
        // 사용자 정보 조회
        User user = getUser(userId);

        // 마지막 동물 정보 조회
        Animal lastAnimal = animalRepository.findTopByOrderByIdDesc()
                .orElseThrow(() -> new AnimalException(ANIMAL_NULL));

        // 해당 지역의 동물 목록 조회
        List<Animal> animals = animalRepository.findByRegionAndIdGreaterThanOrderByIdDesc(user.getRegion(), lastAnimalId);
        if (animals.isEmpty()) {
            throw new AlarmException(ALARM_NULL);
        }

        return AlarmResponse.builder()
                .lastAnimal(lastAnimal.getId())
                .animal(animals.stream()
                        .map(animal -> AnimalDto.builder()
                                .animalId(animal.getId())
                                .status(animal.getStatus().name())
                                .img(animal.getImage())
                                .breed(animal.getBreed().getBreed())
                                .city(animal.getRegion().getId())
                                .build())
                        .toList())
                .build();
    }
    @Transactional
    public void changeAlarmSetting(Long userId, Boolean enabled, String fcmToken) {
        User user = getUser(userId);
        // 알림 설정 변경
        if (enabled) {
            user.setFcmToken(fcmToken);
        } else {
            // FCM 토큰이 null인 경우 알림 비활성화
            user.setFcmToken(null);
        }
        userRepository.save(user);
    }


    public MyPageResponse myPage(Long userId) {
        User user = getUser(userId);

        List<AnimalUser> myAnimals = animalUserRepository.findByUser(user);


        List<MyAnimalDto> myAnimalDtos = myAnimals.stream()
                .map(animal -> MyAnimalDto.builder()
                        .animalUserId(animal.getId())
                        .img(animal.getImage())
                        .name(animal.getName())
                        .build())
                .toList();

        return MyPageResponse.builder()
                .email(user.getEmail())
                .city(user.getRegion().getId())
                .myAnimal(myAnimalDtos)
                .build();
    }

    public MyPostResponse myPost(Long userId) {
        User user = getUser(userId);

        List<Animal> myPosts = animalRepository.findByUser(user);

        List<AnimalDto> myPostDtos = myPosts.stream()
                .map(animal -> AnimalDto.builder()
                        .animalId(animal.getId())
                        .status(animal.getStatus().name())
                        .img(animal.getImage())
                        .breed(animal.getBreed().getBreed())
                        .city(animal.getRegion().getId())
                        .gender(animal.getGender().name())
                        .occurredAt(animal.getOccurredAt().toString())
                        .build())
                .toList();
        return MyPostResponse.builder()
                .postAnimal(myPostDtos)
                .build();
    }

    public MyScrapResponse myScrap(Long userId) {
        User user = getUser(userId);

        List<Animal> myScraps = scrapRepository.findAnimalsByUser(user);

        List<AnimalDto> myScrapDtos = myScraps.stream()
                .map(animal -> AnimalDto.builder()
                        .animalId(animal.getId())
                        .status(animal.getStatus().name())
                        .img(animal.getImage())
                        .breed(animal.getBreed().getBreed())
                        .city(animal.getRegion().getId())
                        .gender(animal.getGender().name())
                        .occurredAt(animal.getOccurredAt().toString())
                        .build())
                .toList();

        return MyScrapResponse.builder()
                .postAnimal(myScrapDtos)
                .build();
    }

    @Transactional
    public MyEmailResponse changeEmail(long userId, String email, String accessToken, @NotBlank(message = "refreshToken: {NotBlank}") String refreshToken) {
        User user = getUser(userId);
        // 이메일 중복 확인
        if (userRepository.existsByEmail(email)) {
            throw new AuthException(DUPLICATE_EMAIL);
        }
        user.setEmail(email);

        String new_accessToken = jwtProvider.createToken_changeEmail(email, userId, accessToken);
        String new_refreshToken = jwtProvider.createToken_changeEmail(email, userId, refreshToken);
        user.setRefreshToken(new_refreshToken);
        // 이메일 변경
        userRepository.save(user);
        return MyEmailResponse.builder()
                .accessToken(new_accessToken)
                .refreshToken(new_refreshToken)
                .build();

    }

    @Transactional
    public void changeCity(long userId, Long city) {
        User user = getUser(userId);
        Region region = regionRepository.findById(city)
                .orElseThrow(() -> new AuthException(REGION_NOT_FOUND));
        user.setRegion(region);
        userRepository.save(user);

    }

    private User getUser(long userId) {
        return userRepository.findById(userId)
                .orElseThrow(() -> new AuthException(USER_NOT_FOUND));
    }



}
