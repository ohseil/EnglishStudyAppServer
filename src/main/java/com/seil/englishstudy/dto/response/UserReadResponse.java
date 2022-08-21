package com.seil.englishstudy.dto.response;

import com.seil.englishstudy.entity.Favorite;
import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

import java.time.LocalDateTime;
import java.util.Set;

@Getter
@Builder
@EqualsAndHashCode
@RequiredArgsConstructor
public class UserReadResponse {

    private final Long userId;
    private final String email;
    private final String name;
    private final Set<String> roleList;
    private final LocalDateTime createdDate;
    private final Set<Favorite> favoriteList;
}
