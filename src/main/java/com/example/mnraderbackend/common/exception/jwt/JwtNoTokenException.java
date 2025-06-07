package com.example.mnraderbackend.common.exception.jwt;

import lombok.Getter;
import com.example.mnraderbackend.common.response.status.ResponseStatus;
@Getter
public class JwtNoTokenException extends JwtBadRequestException {

    private final ResponseStatus exceptionStatus;

    public JwtNoTokenException(ResponseStatus exceptionStatus) {
        super(exceptionStatus);
        this.exceptionStatus = exceptionStatus;
    }
}