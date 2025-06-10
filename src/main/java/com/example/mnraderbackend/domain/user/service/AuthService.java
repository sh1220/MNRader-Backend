package com.example.mnraderbackend.domain.user.service;

import com.example.mnraderbackend.common.convert.status.Status;
import com.example.mnraderbackend.common.exception.AuthException;
import com.example.mnraderbackend.common.jwt.JwtProvider;
import com.example.mnraderbackend.common.model.Region;
import com.example.mnraderbackend.common.model.User;
import com.example.mnraderbackend.domain.region.RegionRepository;
import com.example.mnraderbackend.domain.user.dto.LoginRequest;
import com.example.mnraderbackend.domain.user.dto.LoginResponse;
import com.example.mnraderbackend.domain.user.dto.SignUpRequest;
import com.example.mnraderbackend.domain.user.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.NoSuchElementException;

import static com.example.mnraderbackend.common.response.status.BaseExceptionResponseStatus.*;


@Slf4j
@Service
@RequiredArgsConstructor
public class AuthService {

    private final UserRepository userRepository;
    private final RegionRepository regionRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtProvider jwtProvider;

    @Transactional
    public LoginResponse login(LoginRequest authRequest) {

        String email = authRequest.getEmail();

        // TODO: 1. 이메일 유효성 확인
        User user;
        try {
            user = userRepository.findByEmail(email)
                    .orElseThrow(() -> new AuthException(EMAIL_NOT_FOUND));
        } catch (NoSuchElementException e) {
            throw new AuthException(EMAIL_NOT_FOUND);
        }
        long userId = user.getId();

        // TODO: 2. 비밀번호 일치 확인
        validatePassword(authRequest.getPassword(), userId);

        // TODO: 3. JWT 갱신
        String updatedAccessToken = jwtProvider.createToken(email, userId);
        String updatedRefreshToken = jwtProvider.createRefreshToken(email, userId);
        user.setRefreshToken(updatedRefreshToken);
        userRepository.save(user);

        return new LoginResponse(updatedAccessToken, updatedRefreshToken);
    }

    private void validatePassword(String password, long userId) {
        String encodedPassword = userRepository.findById(userId).map(User::getPassword)
                .orElseThrow(() -> new AuthException(USER_NOT_FOUND));
        if (!passwordEncoder.matches(password, encodedPassword)) {
            throw new AuthException(PASSWORD_NO_MATCH);
        }
    }


    @Transactional
    public void signUp(SignUpRequest signUpRequest) {
        // email, name 유효성 검사
        validateEmail(signUpRequest.getEmail());

        // Encrypt password
        String encodedPassword = passwordEncoder.encode(signUpRequest.getPassword());
        signUpRequest.setPassword(encodedPassword);

        // get region from request
        Region region = regionRepository.findById(signUpRequest.getCity())
                .orElseThrow(() -> new AuthException(REGION_NOT_FOUND));


        // Create user
        User user = User.builder()
                .email(signUpRequest.getEmail())
                .password(encodedPassword)
                .region(region)
                .status(Status.ACTIVE)
                .build();

        userRepository.save(user);

    }


    public void validateEmail(String email) {
        if (userRepository.existsByEmail(email)) {
            throw new AuthException(DUPLICATE_EMAIL);
        }
    }

    // jwt 토큰에서 이메일을 가져와서 유저 아이디를 반환하는 용도
    public long getUserIdByEmailAndValidate(String email) {
        return userRepository.findByEmail(email).
                orElseThrow(() -> new AuthException(INVALID_TOKEN))
                .getId();
    }



//    public RefreshResponse refresh(String refreshToken){
//        // 만료 & 유효성 확인, 로그아웃 확인
//        if(jwtProvider.isExpiredToken(refreshToken) || refreshToken == null || refreshToken.isEmpty()){
//            throw new JwtExpiredTokenException(EXPIRED_REFRESH_TOKEN);
//        }
//        String email = jwtProvider.getPrincipal(refreshToken);
//        if (email == null) {
//            throw new JwtInvalidTokenException(INVALID_TOKEN);
//        }
//
//        // 이메일 유효성 확인
//        User user;
//        try {
//            user = userRepository.getUserByEmail(email).get();
//        } catch (IncorrectResultSizeDataAccessException e) {
//            throw new AuthException(EMAIL_NOT_FOUND);
//        }
//        long userId = user.getUserId();
//
//        // 엑세스 토큰 재발급
//        return new RefreshResponse(jwtProvider.createToken(email, userId));
//    }

//    // 로그아웃
//    public void logout(long userId) {
//
//        User user;
//        try {
//            user = userRepository.getUserByUserId(userId).get();
//        } catch (NoSuchElementException e) {
//            throw new AuthException(USER_NOT_FOUND);
//        }
//        user.setRefreshToken(null);
//        userRepository.save(user);
//    }







}
