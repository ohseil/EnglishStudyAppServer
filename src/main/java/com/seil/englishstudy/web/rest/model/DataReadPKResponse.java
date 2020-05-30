package com.seil.englishstudy.web.rest.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

@Builder
@Data
@AllArgsConstructor
public class DataReadPKResponse {
    long pk;
}
