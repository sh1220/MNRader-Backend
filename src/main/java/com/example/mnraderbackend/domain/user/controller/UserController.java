package com.example.mnraderbackend.domain.user.controller;

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

    @GetMapping("/home")
    public BaseResponse<HomeResponse> home(
            @PreAuthorize Long userId
    ) {
        return new BaseResponse<>(HOME_SUCCESS, userService.home(userId));
    }



    @GetMapping("/alarm")
    public BaseResponse<AlarmResponse> alarm(
            @PreAuthorize Long userId,
            @RequestParam("last") Long lastAnimalId
    ) {
        return new BaseResponse<>(ALARM_SUCCESS, userService.alarm(userId, lastAnimalId));
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
    public BaseResponse<Object> changeEmail(
            @PreAuthorize long userId,
            @Validated @RequestBody MyEmailRequest emailRequest,
            BindingResult bindingResult
    ){
        if (bindingResult.hasErrors()) {
            throw new UserException(INVALID_USER_VALUE, getErrorMessages(bindingResult));
        }
        userService.changeEmail(userId, emailRequest.getEmail());
        return new BaseResponse<>(MY_UPDATE_EMAIL_SUCCESS);
    }


}