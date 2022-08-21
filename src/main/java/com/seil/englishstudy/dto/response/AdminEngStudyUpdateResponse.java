package com.seil.englishstudy.dto.response;

import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@Builder
@EqualsAndHashCode
@RequiredArgsConstructor
public class AdminEngStudyUpdateResponse {

    private final long categoryCode;
    private final String question;
    private final String answer;
}
