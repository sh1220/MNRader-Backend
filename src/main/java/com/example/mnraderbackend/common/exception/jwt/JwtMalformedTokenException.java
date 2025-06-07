package com.example.mnraderbackend.common.exception.jwt;

import lombok.Getter;
import com.example.mnraderbackend.common.response.status.ResponseStatus;
@Getter
public class JwtMalformedTokenException extends JwtUnauthorizedTokenException {

    private final ResponseStatus exceptionStatus;

    public JwtMalformedTokenException(ResponseStatus exceptionStatus) {
        super(exceptionStatus);
        this.exceptionStatus = exceptionStatus;
    }
}