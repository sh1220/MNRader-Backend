package com.example.mnraderbackend.domain.user.controller;

import com.example.mnraderbackend.common.exception.AuthException;
import com.example.mnraderbackend.domain.user.dto.LoginRequest;
import com.example.mnraderbackend.domain.user.dto.LoginResponse;
import com.example.mnraderbackend.domain.user.dto.SignUpRequest;
import com.example.mnraderbackend.domain.user.service.AuthService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import com.example.mnraderbackend.common.response.BaseResponse;

import static com.example.mnraderbackend.common.response.status.BaseExceptionResponseStatus.*;
import static com.example.mnraderbackend.common.util.BindingResultUtils.getErrorMessages;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/auth")
public class AuthController {

    
    private final AuthService authService;

//    @PostMapping("/refresh")
//    public BaseResponse<RefreshResponse> refresh(@Validated @RequestBody RefreshRequest refreshRequest) {
//        return new BaseResponse<>(authService.refresh(refreshRequest.getRefreshToken()));
//    }

    @PostMapping("/signup")
    public BaseResponse<Object> signUp(@Valid @RequestBody SignUpRequest signUpRequest, BindingResult bindingResult) {
        if(bindingResult.hasErrors()){
            throw new AuthException(INVALID_USER_VALUE, getErrorMessages(bindingResult));
        }
        authService.signUp(signUpRequest);
        return new BaseResponse<>(SIGNUP_SUCCESS);

    }
//

    /**
     * 로그인
     */
    @PostMapping("/login")
    public BaseResponse<LoginResponse> login(@Validated @RequestBody LoginRequest authRequest, BindingResult bindingResult) {
        log.info("[AuthController.login]");
        if (bindingResult.hasErrors()) {
            throw new AuthException(INVALID_USER_VALUE, getErrorMessages(bindingResult));
        }
        return new BaseResponse<>(SIGNIN_SUCCESS, authService.login(authRequest));
    }

    /**
     * 로그아웃 : db의 refresh 토큰을 null로 설정
     */
//    @PatchMapping("/logout")
//    public BaseResponse<Object> logout(@PreAuthorize long userId) {
//        authService.logout(userId);
//        return new BaseResponse<>(null);
//    }
//



}
