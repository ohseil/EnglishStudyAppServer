package com.seil.englishstudy.web.rest.exception;

import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
public class UserDataException extends  RuntimeException {

    private HttpStatus httpStatus;
    private String errorCode;

    public UserDataException(HttpStatus httpstatus, String errorcode, String msg) {
        super(msg);
        this.httpStatus = httpstatus;
        this.errorCode = errorcode;
    }
}
