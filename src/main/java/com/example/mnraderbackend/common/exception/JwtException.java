package com.example.mnraderbackend.common.exception;

import lombok.Getter;
import com.example.mnraderbackend.common.response.status.ResponseStatus;
@Getter
public class JwtException extends RuntimeException {

    private final ResponseStatus exceptionStatus;

    public JwtException(ResponseStatus exceptionStatus) {
        super(exceptionStatus.getMessage());
        this.exceptionStatus = exceptionStatus;
    }
}