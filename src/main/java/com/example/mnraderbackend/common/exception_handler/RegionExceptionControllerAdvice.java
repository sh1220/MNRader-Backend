package com.example.mnraderbackend.common.exception_handler;

import com.example.mnraderbackend.common.exception.RegionException;
import com.example.mnraderbackend.common.exception.UserException;
import com.example.mnraderbackend.common.response.BaseErrorResponse;
import jakarta.annotation.Priority;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@Slf4j
@Priority(0)
@RestControllerAdvice
public class RegionExceptionControllerAdvice {

    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(RegionException.class)
    public BaseErrorResponse handle_RegionException(RegionException e) {
        log.error("[handle_RegionException] {}", e.getMessage());
        return new BaseErrorResponse(e.getExceptionStatus());
    }
}