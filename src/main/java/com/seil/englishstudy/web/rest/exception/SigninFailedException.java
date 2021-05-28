package com.seil.englishstudy.web.rest.exception;

import org.springframework.http.HttpStatus;

public class SigninFailedException extends RuntimeException {
    private HttpStatus httpStatus;
    private String errorCode;

    public SigninFailedException(HttpStatus httpstatus, String errorcode, String msg) {
        super(msg);
        this.httpStatus = httpstatus;
        this.errorCode = errorcode;
    }

    public HttpStatus getHttpStatus() {return this.httpStatus;}
    public String getErrorCode() {return this.errorCode;}
    /*public SigninFailedException(String msg, Throwable t) {
        super(msg, t);
    }
    public SigninFailedException(String msg) {
        super(msg);
    }
    public SigninFailedException() {
        super();
    }*/
}