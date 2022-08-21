package com.seil.englishstudy.web.rest.exception;

import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
public class FavoriteException extends RuntimeException {

    private HttpStatus httpStatus;
    private String errorCode;

    public FavoriteException(HttpStatus httpstatus, String errorcode, String msg) {
        super(msg);
        this.httpStatus = httpstatus;
        this.errorCode = errorcode;
    }
}
