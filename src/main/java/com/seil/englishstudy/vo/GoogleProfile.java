package com.seil.englishstudy.vo;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class GoogleProfile {

    private final String name;
    private final String email;

}
