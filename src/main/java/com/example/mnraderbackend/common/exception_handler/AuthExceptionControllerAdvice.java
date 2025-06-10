package com.example.mnraderbackend.common.exception_handler;

import com.example.mnraderbackend.common.exception.AuthException;
import com.example.mnraderbackend.common.response.BaseErrorResponse;
import jakarta.annotation.Priority;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@Slf4j
@Priority(0)
@RestControllerAdvice //모든 컨트롤러에서 발생하는 예외를 전역적으로 처리
public class AuthExceptionControllerAdvice {

    @ResponseStatus(HttpStatus.BAD_REQUEST) //해당 메서드의 예외 발생시 보낼
    @ExceptionHandler(AuthException.class)
    public BaseErrorResponse handle_AuthException(AuthException e){
        log.error("[handle_AuthException]", e.getMessage());
        return new BaseErrorResponse(e.getExceptionStatus(), e.getMessage());
    }
}
