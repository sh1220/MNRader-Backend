package com.example.mnraderbackend.domain.animal.service;

import com.example.mnraderbackend.common.argument_resolver.PreAuthorize;
import com.example.mnraderbackend.common.exception.AlarmException;
import com.example.mnraderbackend.common.exception.AnimalException;
import com.example.mnraderbackend.common.exception.AuthException;
import com.example.mnraderbackend.common.jwt.JwtProvider;
import com.example.mnraderbackend.common.model.*;
import com.example.mnraderbackend.domain.animal.dto.AnimalDetailResponse;
import com.example.mnraderbackend.domain.animal.dto.AnimalRegisterRequest;
import com.example.mnraderbackend.domain.animal.repository.AnimalRepository;
import com.example.mnraderbackend.domain.animalUser.AnimalUserRepository;
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
public class AnimalService {
    private final UserRepository userRepository;
    private final AnimalRepository animalRepository;
    private final RegionRepository regionRepository;
    private final ScrapRepository scrapRepository;


    public AnimalDetailResponse getAnimalDetail(Long animalId) {
        return null;
    }

    @Transactional
    public void registerAnimal(AnimalRegisterRequest request, Long userId) {
    }

    @Transactional
    public void scrapAnimal(Long animalId, Long userId) {
    }

    @Transactional
    public void cancelScrap(Long animalId, Long userId) {
    }

    private User getUser(long userId) {
        return userRepository.findById(userId)
                .orElseThrow(() -> new AuthException(USER_NOT_FOUND));
    }
}
