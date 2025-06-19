package com.example.mnraderbackend.domain.user.controller;

import com.example.mnraderbackend.common.argument_resolver.PreAccessToken;
import com.example.mnraderbackend.common.argument_resolver.PreAuthorize;
import com.example.mnraderbackend.common.exception.UserException;
import com.example.mnraderbackend.common.response.BaseResponse;
import com.example.mnraderbackend.domain.user.dto.*;
import com.example.mnraderbackend.domain.user.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import static com.example.mnraderbackend.common.response.status.BaseExceptionResponseStatus.*;
import static com.example.mnraderbackend.common.util.BindingResultUtils.getErrorMessages;

@RestController
@RequestMapping("/users")
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;

    @PostMapping("/alarmToken")
    public BaseResponse<Object> registerFcmToken(
            @PreAuthorize Long userId,
            @RequestBody FcmTokenRequest request
    ) {
        userService.registerFcmToken(userId, request.getFcmToken());
        return new BaseResponse<>(FCM_TOKEN_POST_SUCCESS);
    }
    @GetMapping("/alarm")
    public BaseResponse<AlarmResponse> getAlarmPage(
            @PreAuthorize Long userId,
            @RequestParam("last") Long lastAnimalId
    ) {
        return new BaseResponse<>(ALARM_SUCCESS, userService.getAlarmPage(userId, lastAnimalId));
    }

    @PatchMapping("/alarm")
    public BaseResponse<Object> changeAlarmSetting(
            @PreAuthorize Long userId,
            @Validated @RequestBody AlarmRequest alarmRequest,
            BindingResult bindingResult
    ) {
        if (bindingResult.hasErrors()) {
            throw new UserException(INVALID_USER_VALUE, getErrorMessages(bindingResult));
        }
        userService.changeAlarmSetting(userId, alarmRequest.getEnabled(), alarmRequest.getFcmToken());
        return new BaseResponse<>(MY_UPDATE_ALARM_SUCCESS);
    }

    @GetMapping("/home")
    public BaseResponse<HomeResponse> home(
            @PreAuthorize Long userId,
            @RequestParam(required = false) Long breed,
            @RequestParam(required = false) Long city
    ) {
        return new BaseResponse<>(HOME_SUCCESS, userService.home(userId, breed, city));
    }




    @GetMapping("/my/page")
    public BaseResponse<MyPageResponse> myPage(@PreAuthorize Long userId) {
        return new BaseResponse<>(HOME_SUCCESS, userService.myPage(userId));
    }

    @GetMapping("/my/posts")
    public BaseResponse<MyPostResponse> myPost(@PreAuthorize Long userId) {
        return new BaseResponse<>(MY_POST_SUCCESS, userService.myPost(userId));
    }

    @GetMapping("/my/scrap")
    public BaseResponse<MyScrapResponse> myScrap(@PreAuthorize Long userId) {
        return new BaseResponse<>(MY_SCRAP_SUCCESS, userService.myScrap(userId));
    }

    @PatchMapping("/my/email")
    public BaseResponse<MyEmailResponse> changeEmail(
            @PreAuthorize long userId,
            @PreAccessToken String accessToken,
            @Validated @RequestBody MyEmailRequest myEmailRequest,
            BindingResult bindingResult
    ) {
        if (bindingResult.hasErrors()) {
            throw new UserException(INVALID_USER_VALUE, getErrorMessages(bindingResult));
        }

        return new BaseResponse<>(
                MY_UPDATE_EMAIL_SUCCESS,
                userService.changeEmail(userId, myEmailRequest.getEmail(), accessToken, myEmailRequest.getRefreshToken())
        );
    }

    @PatchMapping("/my/city")
    public BaseResponse<Object> changeCity(
            @PreAuthorize Long userId,
            @Validated @RequestBody MyRegionRequest myRegionRequest,
            BindingResult bindingResult
    ) {
        if (bindingResult.hasErrors()) {
            throw new UserException(INVALID_USER_VALUE, getErrorMessages(bindingResult));
        }
        userService.changeCity(userId, myRegionRequest.getCity());
        return new BaseResponse<>(MY_UPDATE_REGION_SUCCESS);
    }


}