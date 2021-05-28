package com.seil.englishstudy.web.rest.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.validator.constraints.Length;

import javax.validation.constraints.Max;
import javax.validation.constraints.Min;

// Data CREATE 요청 구조 정의
@Builder
@Data
@AllArgsConstructor
@NoArgsConstructor
public class DataCreateRequest {

    @Min(value = 0, message = "카테고리 코드는 0번부터 1000번까지 입니다.")
    @Max(value = 1000, message = "카테고리 코드는 0번부터 1000번까지 입니다.")
    private long categoryCode;

    @Length(min = 0, max = 500, message = "문장의 길이는 최대 500글자 입니다.")
    private String question;

    @Length(min = 0, max = 500, message = "문장의 길이는 최대 500글자 입니다.")
    private String answer;
}