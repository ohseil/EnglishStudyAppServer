package com.seil.englishstudy.service.favorite;

import com.seil.englishstudy.dto.response.FavoritesReadResponse;
import com.seil.englishstudy.entity.Favorite;
import com.seil.englishstudy.entity.User;
import com.seil.englishstudy.web.rest.exception.ErrorCode;
import com.seil.englishstudy.web.rest.exception.StudyDataException;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

@RequiredArgsConstructor
@Service
public class FavoritesReadService {

    @Transactional(readOnly = true)
    public List<FavoritesReadResponse> getFavoritesForUser(final User user) {

        if (user.getFavoriteList().isEmpty() == true)
            throw new StudyDataException(HttpStatus.NOT_FOUND, ErrorCode.NOT_EXIST_DATA, "not exist data.");

        List<FavoritesReadResponse> favoritesReadResponseList = new ArrayList<>();

        for (Favorite favorite : user.getFavoriteList()) {
            favoritesReadResponseList.add(FavoritesReadResponse.builder()
                    .favoriteId(favorite.getId())
                    .categoryCode(favorite.getEngStudyData().getCategoryCode())
                    .question(favorite.getEngStudyData().getQuestion())
                    .answer(favorite.getEngStudyData().getAnswer())
                    .build());
        }

        return favoritesReadResponseList;
    }
}
