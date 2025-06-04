package com.example.mnraderbackend.common.response;

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

