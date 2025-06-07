package com.example.mnraderbackend.common.exception.jwt;

import lombok.Getter;
import com.example.mnraderbackend.common.response.status.ResponseStatus;
@Getter
public class JwtUnsupportedTokenException extends JwtBadRequestException {

    private final ResponseStatus exceptionStatus;

    public JwtUnsupportedTokenException(ResponseStatus exceptionStatus) {
        super(exceptionStatus);
        this.exceptionStatus = exceptionStatus;
    }
}