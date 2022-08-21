package com.seil.englishstudy.web.rest.advice;

import com.seil.englishstudy.web.rest.exception.*;
import com.seil.englishstudy.dto.response.ErrorResponse;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class ApiRestAdvice {

    /*
     * UserData 관련 예외 처리.
     */
    @ExceptionHandler(UserDataException.class)
    public ResponseEntity<ErrorResponse> UserDataExceptionHandler (UserDataException e) {

        final ErrorResponse response = ErrorResponse.builder()
                .msg(e.getMessage())
                .code(e.getErrorCode())
                .status(e.getHttpStatus().value())
                .build();

        return new ResponseEntity<>(response, e.getHttpStatus());
    }

    /*
     * EngStudyData 관련 예외 처리.
     */
    @ExceptionHandler(StudyDataException.class)
    public ResponseEntity<ErrorResponse> StudyDataExceptionHandler (StudyDataException e) {

        final ErrorResponse response = ErrorResponse.builder()
                .msg(e.getMessage())
                .code(e.getErrorCode())
                .status(e.getHttpStatus().value())
                .build();

        return new ResponseEntity<>(response, e.getHttpStatus());
    }

    /*
     * Favorite 관련 예외 처리.
     */
    @ExceptionHandler(FavoriteException.class)
    public ResponseEntity<ErrorResponse> StudyDataExceptionHandler (FavoriteException e) {

        final ErrorResponse response = ErrorResponse.builder()
                .msg(e.getMessage())
                .code(e.getErrorCode())
                .status(e.getHttpStatus().value())
                .build();

        return new ResponseEntity<>(response, e.getHttpStatus());
    }

    /*
     * Sign in 관련 예외 처리.
     */
    @ExceptionHandler(SigninFailedException.class)
    public ResponseEntity<ErrorResponse> SigninFailedExceptionHandler (SigninFailedException e) {

        final ErrorResponse response = ErrorResponse.builder()
                .msg(e.getMessage())
                .code(e.getErrorCode())
                .status(e.getHttpStatus().value())
                .build();

        return new ResponseEntity<>(response, e.getHttpStatus());
    }

    /*
     * Request 객체의 필드 valid 예외 처리
     */
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ErrorResponse> MethodArgumentNotValidExceptionHandler (MethodArgumentNotValidException e) {

        final ErrorResponse response = ErrorResponse.builder()
                .msg(e.getMessage())
                .code(ErrorCode.REQUEST_NOT_VALID)
                .status(HttpStatus.BAD_REQUEST.value())
                .build();

        return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
    }

    /*
     * 처리하지 않은 나머지 예외들 예외 처리.
     */
    @ExceptionHandler(Exception.class)
    public ResponseEntity<ErrorResponse> AllExceptionHandler (Exception e) {

        final ErrorResponse response = ErrorResponse.builder()
                .msg(e.getMessage())
                .code(ErrorCode.INTERNAL_SERVER_ERROR)
                .status(HttpStatus.INTERNAL_SERVER_ERROR.value())
                .build();

        return new ResponseEntity<>(response, HttpStatus.INTERNAL_SERVER_ERROR);
    }
}
