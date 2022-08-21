package com.seil.englishstudy.dto.response;

import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@Builder
@EqualsAndHashCode
@RequiredArgsConstructor
public class FavoritesReadResponse {

    private final Long favoriteId;
    private final long categoryCode;
    private final String question;
    private final String answer;
}
