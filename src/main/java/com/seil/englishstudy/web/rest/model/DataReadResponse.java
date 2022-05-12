package com.seil.englishstudy.web.rest.model;

import com.seil.englishstudy.entity.EnglishStudyData;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Builder
@Data
@NoArgsConstructor
@AllArgsConstructor
public class DataReadResponse {

    private List<EnglishStudyData> dataList;
}
