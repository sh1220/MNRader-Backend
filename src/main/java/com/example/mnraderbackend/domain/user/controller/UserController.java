package com.example.mnraderbackend.domain.user.controller;

import com.example.mnraderbackend.common.argument_resolver.PreAuthorize;
import com.example.mnraderbackend.common.response.BaseResponse;
import com.example.mnraderbackend.domain.user.dto.FcmTokenRequest;
import com.example.mnraderbackend.domain.user.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import static com.example.mnraderbackend.common.response.status.BaseExceptionResponseStatus.FCM_TOKEN_POST_SUCCESS;

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
}