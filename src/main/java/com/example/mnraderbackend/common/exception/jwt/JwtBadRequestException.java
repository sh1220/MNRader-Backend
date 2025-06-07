package com.example.mnraderbackend.common.exception.jwt;

import lombok.Getter;
import com.example.mnraderbackend.common.response.status.ResponseStatus;
@Getter
public class JwtBadRequestException extends RuntimeException {

    private final ResponseStatus exceptionStatus;

    public JwtBadRequestException(ResponseStatus exceptionStatus) {
        super(exceptionStatus.getMessage());
        this.exceptionStatus = exceptionStatus;
    }
}