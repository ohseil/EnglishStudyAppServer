package com.seil.englishstudy.web.rest.model;

import lombok.Builder;
import lombok.Getter;

@Builder
@Getter
public class ErrorResponse {

    private String msg;
    private int status;
    private String code;
}
