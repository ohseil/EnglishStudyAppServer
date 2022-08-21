package com.seil.englishstudy.service.favorite;

import com.seil.englishstudy.entity.Favorite;
import com.seil.englishstudy.entity.User;
import com.seil.englishstudy.repository.FavoriteRepository;
import com.seil.englishstudy.web.rest.exception.ErrorCode;
import com.seil.englishstudy.web.rest.exception.FavoriteException;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@RequiredArgsConstructor
@Service
public class FavoriteDeleteService {

    private final FavoriteRepository favoriteRepository;

    @Transactional
    public void deleteFavoriteForUser(final Long favoriteId, final User user) {

        Optional<Favorite> favorite = favoriteRepository.findById(favoriteId);

        favorite.orElseThrow(() -> new FavoriteException(HttpStatus.NOT_FOUND, ErrorCode.NOT_EXIST_DATA, "not exist data."));

        user.removeFavorite(favorite.get());

    }
}
