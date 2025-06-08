package com.example.mnraderbackend.common.response;

import com.example.mnraderbackend.common.response.status.BaseExceptionResponseStatus;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import lombok.Builder;
import lombok.Getter;

import com.example.mnraderbackend.common.response.status.ResponseStatus;

import static com.example.mnraderbackend.common.response.status.BaseExceptionResponseStatus.SUCCESS;

@Getter
@JsonPropertyOrder({"code", "status", "message", "result"})
public class BaseResponse<T> implements ResponseStatus {

    private final int code;
    private final int status;
    private final String message;

    @JsonInclude(JsonInclude.Include.NON_NULL)
    private final T result;

    public BaseResponse(ResponseStatus responseStatus) {
        this.code = responseStatus.getCode();
        this.status = responseStatus.getStatus();
        this.message = responseStatus.getMessage();
        result = null;
    }

    public BaseResponse(ResponseStatus responseStatus, T result) {
        this.code = responseStatus.getCode();
        this.status = responseStatus.getStatus();
        this.message = responseStatus.getMessage();
        this.result = result;
    }

    // enum 기반 커스텀 응답 생산자
    public BaseResponse(BaseExceptionResponseStatus status, T result) {
        this.code = status.getCode();
        this.status = status.getStatus();
        this.message = status.getMessage();
        this.result = result;
    }

    // enum 기반 커스텀 응답 생산자
    public BaseResponse(BaseExceptionResponseStatus status, T result) {
        this.code = status.getCode();
        this.status = status.getStatus();
        this.message = status.getMessage();
        this.result = result;
    }

    @Override
    public int getCode() {
        return code;
    }

    @Override
    public int getStatus() {
        return status;
    }

    @Override
    public String getMessage() {
        return message;
    }

}

