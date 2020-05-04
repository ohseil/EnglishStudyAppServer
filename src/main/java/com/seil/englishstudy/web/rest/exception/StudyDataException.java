package com.seil.englishstudy.web.rest.exception;

import lombok.Builder;
import lombok.Data;
import org.springframework.http.HttpStatus;

public class StudyDataException extends RuntimeException {

    private HttpStatus httpStatus;
    private String errorCode;

    public StudyDataException(HttpStatus httpstatus, String errorcode, String msg) {
        super(msg);
        this.httpStatus = httpstatus;
        this.errorCode = errorcode;
    }

    public HttpStatus getHttpStatus() {return this.httpStatus;}
    public String getErrorCode() {return this.errorCode;}
}
