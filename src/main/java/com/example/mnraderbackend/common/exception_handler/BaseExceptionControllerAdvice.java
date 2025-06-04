package com.example.mnraderbackend.common.exception_handler;

// import jakarta.validation.ConstraintViolationException;

import com.example.mnraderbackend.common.response.BaseErrorResponse;
import lombok.extern.slf4j.Slf4j;
import org.apache.coyote.BadRequestException;
import org.springframework.beans.TypeMismatchException;
import org.springframework.http.HttpStatus;
import org.springframework.web.HttpRequestMethodNotSupportedException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.multipart.MaxUploadSizeExceededException;
import org.springframework.web.servlet.NoHandlerFoundException;

import static com.example.mnraderbackend.common.response.status.BaseExceptionResponseStatus.METHOD_NOT_ALLOWED;
import static com.example.mnraderbackend.common.response.status.BaseExceptionResponseStatus.URL_NOT_FOUND;
import static com.example.mnraderbackend.common.response.status.BaseExceptionResponseStatus.SERVER_ERROR;
import static com.example.mnraderbackend.common.response.status.BaseExceptionResponseStatus.FILE_TOO_LARGE;


@Slf4j
@RestControllerAdvice
public class BaseExceptionControllerAdvice {

    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler({BadRequestException.class, NoHandlerFoundException.class, TypeMismatchException.class})
    public BaseErrorResponse handle_BadRequest(Exception e) {
        log.error("[handle_BadRequest]", e);
        return new BaseErrorResponse(URL_NOT_FOUND);
    }


    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(HttpRequestMethodNotSupportedException.class)
    public BaseErrorResponse handle_HttpRequestMethodNotSupportedException(HttpRequestMethodNotSupportedException e) {
        log.error("[handle_HttpRequestMethodNotSupportedException]", e);
        return new BaseErrorResponse(METHOD_NOT_ALLOWED);
    }



    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    @ExceptionHandler(RuntimeException.class)
    public BaseErrorResponse handle_RuntimeException(Exception e) {
        log.error("[handle_RuntimeException]", e);
        return new BaseErrorResponse(SERVER_ERROR);
    }

    @ResponseStatus(HttpStatus.PAYLOAD_TOO_LARGE)
    @ExceptionHandler(MaxUploadSizeExceededException.class)
    public BaseErrorResponse handleMaxSizeException(MaxUploadSizeExceededException e) {
        return new BaseErrorResponse(FILE_TOO_LARGE);
    }

}