package com.seil.englishstudy.dto.response;

import lombok.Builder;
import lombok.Getter;


@Getter
@Builder
public class ErrorResponse {

    private final String msg;
    private final int status;
    private final String code;
}
