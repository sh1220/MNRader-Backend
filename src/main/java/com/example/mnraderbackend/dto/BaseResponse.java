package com.example.mnraderbackend.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class BaseResponse<T> {
    private int code;
    private int status;
    private String message;
    private T result;
}
