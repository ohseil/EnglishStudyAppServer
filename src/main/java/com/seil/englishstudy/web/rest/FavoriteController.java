package com.seil.englishstudy.web.rest;

import com.seil.englishstudy.entity.User;
import com.seil.englishstudy.service.favorite.FavoriteAddService;
import com.seil.englishstudy.service.favorite.FavoriteDeleteService;
import com.seil.englishstudy.service.favorite.FavoritesReadService;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.net.URI;

@RequiredArgsConstructor
@RequestMapping(value = "/favorites")
@RestController
public class FavoriteController {

    private final FavoriteAddService favoriteAddService;
    private final FavoritesReadService favoritesReadService;
    private final FavoriteDeleteService favoriteDeleteService;

    @ApiImplicitParams({
            @ApiImplicitParam(name = "X-AUTH-TOKEN", value = "jwt after sign in", required = true, dataType = "String", paramType = "header")
    })
    @PostMapping
    public ResponseEntity addFavorite(@RequestBody final Long engStudyDataId, @AuthenticationPrincipal final User user) {
        Long favoriteId = favoriteAddService.addFavoriteForUser(engStudyDataId, user);
        return ResponseEntity.created(URI.create("/favorites/" + favoriteId)).build();
    }

    @ApiImplicitParams({
            @ApiImplicitParam(name = "X-AUTH-TOKEN", value = "jwt after sign in", required = true, dataType = "String", paramType = "header")
    })
    @GetMapping
    public ResponseEntity getFavoirites(@AuthenticationPrincipal final User user) {
        return ResponseEntity.ok(favoritesReadService.getFavoritesForUser(user));
    }

    @ApiImplicitParams({
            @ApiImplicitParam(name = "X-AUTH-TOKEN", value = "jwt after sign in", required = true, dataType = "String", paramType = "header")
    })
    @DeleteMapping(value = "/{favoriteId}")
    public ResponseEntity deleteFavorite(@PathVariable final Long favoriteId, @AuthenticationPrincipal final User user) {
        favoriteDeleteService.deleteFavoriteForUser(favoriteId, user);
        return ResponseEntity.noContent().build();
    }
}
