package com.seil.englishstudy.dto.request;

import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.hibernate.validator.constraints.Length;

import javax.validation.constraints.Max;
import javax.validation.constraints.Min;

@Getter
@Builder
@EqualsAndHashCode
@RequiredArgsConstructor
public class AdminEngStudyCreateRequest {

    @Min(value = 1, message = "category code must be from 1 to 143.")
    @Max(value = 143, message = "category code must be from 1 to 143.")
    private final long categoryCode;

    @Length(min = 1, max = 380, message = "question length is max 500.")
    private final String question;

    @Length(min = 1, max = 380, message = "answer length is max 500.")
    private final String answer;
}
