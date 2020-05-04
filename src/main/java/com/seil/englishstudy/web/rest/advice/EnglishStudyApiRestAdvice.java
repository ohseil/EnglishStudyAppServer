package com.seil.englishstudy.web.rest.advice;

import com.seil.englishstudy.web.rest.exception.StudyDataException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.HashMap;

@RestControllerAdvice
public class EnglishStudyApiRestAdvice {

    @ExceptionHandler(StudyDataException.class)
    public ResponseEntity StudyDataExceptionHandler (StudyDataException e) {

        HashMap<String, String> map = new HashMap<>();
        map.put("code", e.getErrorCode());
        map.put("msg", e.getMessage());

        return new ResponseEntity<>(map, e.getHttpStatus());
    }
}
